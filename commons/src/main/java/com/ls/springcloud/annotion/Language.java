package com.ls.springcloud.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Language
 * @Description
 * @Author lushuai
 * @Date 2020/1/17 13:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Language {
    String Chinese();
    String English();
}
