package com.example.reflection;

import com.example.reflection.model.Person;
import com.example.reflection.orm.EntityManager;
import com.example.reflection.util.ColumnField;
import com.example.reflection.util.MetaModel;
import com.example.reflection.util.PrimaryKeyField;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ReflectionApplication {

	public static void main(String[] args) throws SQLException, IllegalAccessException {
		SpringApplication.run(ReflectionApplication.class, args);

		MetaModel<Person> metaModel = MetaModel.of(Person.class);

		PrimaryKeyField primaryKeyField = metaModel.getPrimaryKey();
		List<ColumnField> columnFields = metaModel.getColumns();
		System.out.println("");
		System.out.println("");
		System.out.println("Chave primária = " + primaryKeyField.getName() +
				", Tipo = " + primaryKeyField.getType().getSimpleName());

		columnFields.forEach(columnField -> System.out.println("Nome = " + columnField.getName() +
				", Tipo = " + columnField.getType().getSimpleName()));
		System.out.println("");
		System.out.println("");

		/** Criando lista de pessoas*/
		List<Person> people = new ArrayList<>();
		people.add(new Person("João", 12));
		people.add(new Person("Maria", 25));
		people.add(new Person("Jose", 16));
		people.add(new Person("Ana", 44));
		people.add(new Person("Dai", 34));

		writingObjects(people);
		System.out.println("");
		System.out.println("");


	}

	private static void writingObjects(List<Person> people) throws SQLException, IllegalAccessException {

		EntityManager<Person> entityManager = EntityManager.of(Person.class);
		people.forEach(person -> System.out.println(person));

		System.out.println("");
		System.out.println("Gravando no banco");
		System.out.println("");

		for (Person person : people) {
			entityManager.persist(person);
		}

		people.forEach(person -> System.out.println(person));
	}

	private static Person readingObject(Long id){
		EntityManager<Person> entityManager = EntityManager.of(Person.class);

		return null; // entityManager.find(Person.class, id);
	}

}
