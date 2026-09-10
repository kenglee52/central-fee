package com.example.centrol_fee.config;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLoggable {
    String action();
    String entityName();
    String description() default "";
}