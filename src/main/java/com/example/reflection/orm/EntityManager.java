package com.example.reflection.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface EntityManager<T> {

    static <T> EntityManager<T> of(Class<T> classe) {
        return new EntityManagerImpl<>();
    }

    void persist(T t) throws SQLException, IllegalAccessException;

    T find(Class<T> personClass, Object primaryKey) throws SQLException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException;
}
