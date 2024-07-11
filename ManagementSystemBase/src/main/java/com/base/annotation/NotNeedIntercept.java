package com.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD}) // 可以用于类或方法
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNeedIntercept {
}
