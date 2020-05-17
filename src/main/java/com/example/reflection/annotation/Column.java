package com.example.reflection.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Retention define quando a anotaçãoestará diponível
 * COMPILE
 * LOADING
 * RUNTIME
 * */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
}
