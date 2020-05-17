package com.example.reflection.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @Retention definie quando a anotaçãoestará diponível
 *
 * */
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
}
