package com.example.reflection.util;

import com.example.reflection.annotation.PrimaryKey;
import com.example.reflection.annotation.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MetaModel<T> {

    private Class<T> classe;

    public static <T> MetaModel<T> of(Class<T> classe) {
        return new MetaModel(classe);
    }

    public MetaModel(Class<T> classe) {
        this.classe = classe;
    }

    public PrimaryKeyField getPrimaryKey(){
        for(Field field : classe.getDeclaredFields()){
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if(primaryKey != null){
                return  new PrimaryKeyField(field);
            }
        }
        throw new IllegalArgumentException("Nenhuma chave primaria encontrada na classe " + classe.getName());
    }

    public List<ColumnField> getColumns(){
        List<ColumnField> columnFields = new ArrayList<>();
        for(Field field : classe.getDeclaredFields()){
            Column column = field.getAnnotation(Column.class);
            if(column != null){
                ColumnField columnField =  new ColumnField(field);
                columnFields.add(columnField);
            }
        }
        return columnFields;
    }

    public String buildInsertRequest() {
        // insert into Pessoa (id, name, age) value (?, ?, ?)
        String columnElement = buildColumnNames();
        int numeroColunas = getColumns().size() + 1;
        String marcadoresParametros =
        IntStream.range(0, numeroColunas)
                .mapToObj(index-> "?")
                .collect(Collectors.joining(", "));

        return "INSERT INTO " + this.classe.getSimpleName() + " (" + columnElement + ")" +
                "VALUES (" + marcadoresParametros + ")";

    }

    public String buildColumnNames(){
        String nomeChavePrimaria = getPrimaryKey().getName();
        List<String> nomeColunas = getColumns().stream().map(c-> c.getName()).collect(Collectors.toList());
        nomeColunas.add(0, nomeChavePrimaria);

        return String.join(",", nomeColunas);
    }

    public String buildSelectRequest() {
        // select id, name, age from Person
        String columnElement = buildColumnNames();

        return "SELECT " + columnElement + " FROM " + this.classe.getSimpleName() +
                " WHERE " + getPrimaryKey().getName() + " = ?";
    }
}
