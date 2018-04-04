package com.neeson.example.annotation;

import java.lang.annotation.*;

/**
 * Created by daile on 2017/8/14.
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLog {

    String description() default "";

}
