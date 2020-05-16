package com.example.reflection.orm;

import com.example.reflection.util.ColumnField;
import com.example.reflection.util.MetaModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

public class EntityManagerImpl<T> implements EntityManager<T> {

    private AtomicLong idGenerator = new AtomicLong();

    @Override
    public void persist(T t) throws SQLException, IllegalAccessException {
        MetaModel metaModel = MetaModel.of(t.getClass());
        String sql = metaModel.buildInsertRequest();
        PreparedStatement statement = prepareStatementWith(sql).andParameters(t);
        statement.executeUpdate();
    }

    @Override
    public T find(Class<T> tClass, Object primaryKey) throws SQLException, InstantiationException, IllegalAccessException {
        MetaModel metaModel = MetaModel.of(tClass.getClass());
        String sql = metaModel.buildSelectRequest();
        PreparedStatement statement = prepareStatementWith(sql).selectParameters(primaryKey);
        ResultSet resultSet = statement.executeQuery();
        return buildInstanceFrom(tClass, resultSet);
    }

    private T buildInstanceFrom(Class<T> tClass, ResultSet resultSet) throws IllegalAccessException, InstantiationException, SQLException {
        MetaModel metaModel = MetaModel.of(tClass);
        T t = tClass.newInstance();
        Field primaryKeyField = metaModel.getPrimaryKey().getField();
        String primaryKeyColumnName = metaModel.getPrimaryKey().getName();
        Class<?> primaryKeyType = primaryKeyField.getType();
        resultSet.next();
        //Aqui utilizamos o tipo primitivo pois pegamos direto do metamodelo e não foi feito wrapper para Long
        if(primaryKeyType == long.class){
            long primaryKey = resultSet.getInt(primaryKeyColumnName);
            primaryKeyField.setAccessible(true);
            primaryKeyField.set(t, primaryKey);
        }
        return null;
    }

    private PreparedStatementWrapper prepareStatementWith(String sql) throws SQLException {
        Connection connection =
                DriverManager.getConnection(
                        "jdbc:h2:file:./file/data/demo",
                        "sa","1234"
                );
        PreparedStatement statement = connection.prepareStatement(sql);
        return new PreparedStatementWrapper(statement);
    }

    private class PreparedStatementWrapper {
        private PreparedStatement statement;
        public PreparedStatementWrapper(PreparedStatement statement) {
            this.statement = statement;
        }

        public PreparedStatement andParameters(T t) throws SQLException, IllegalAccessException {
            /**
             * Como estamos criando um exemplo utilizamos as  verificações somente com base na classe Pessoa,
             * caso quiser complementar basta adicionar outras verificações e torná-lo mais genérico.
             */
            MetaModel metaModel = MetaModel.of(t.getClass());
            Class<?> tipoChavePrimaria = metaModel.getPrimaryKey().getType();
            if(tipoChavePrimaria == long.class){
                long id = idGenerator.incrementAndGet();
                statement.setLong(1, id);
                Field field = metaModel.getPrimaryKey().getField();
                field.setAccessible(true);
                field.set(t, id);
            }
            for(int i = 0; i < metaModel.getColumns().size(); i++){
                ColumnField columnField = (ColumnField) metaModel.getColumns().get(i);
                Class<?> fieldType = columnField.getType();
                Field field = columnField.getField();
                field.setAccessible(true);
                Object value = field.get(t);
                if(fieldType == int.class){
                    statement.setInt(i + 2, (int) value);
                }else if(fieldType == String.class){
                    statement.setString(i + 2, (String) value);
                }
            }
            return statement;
        }

        public PreparedStatement selectParameters(Object primaryKey) throws SQLException {
            if(primaryKey.getClass() == Long.class){
                statement.setLong(1, (Long) primaryKey);
            }
            return statement;
        }
    }
}
