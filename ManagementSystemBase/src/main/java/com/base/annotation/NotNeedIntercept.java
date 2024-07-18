package com.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 应该放在目标方法或类上
 * 如远程调用时，放在需要被调用的Controller方法上
 * 放在远程调用的方法或类上，不能起作用，仍然会被拦截
 */
@Target({ElementType.TYPE, ElementType.METHOD}) // 可以用于类或方法
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNeedIntercept {
}
