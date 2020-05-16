package com.example.reflection.util;

import java.lang.reflect.Field;

public class PrimaryKeyField {

    private Field field;

    public PrimaryKeyField(Field field) {
        this.field = field;
    }

    public String getName() {
        return this.field.getName();
    }

    public Class<?> getType() {
        return this.field.getType();
    }

    public Field getField() {
        return this.field;
    }
}
