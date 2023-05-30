package com.simplesystem.challenge.simpletodo.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UpdateTodoStatusValidator.class)
public @interface ValidateTodoDtoStatus {
	
	String action() default "";
    String message() default "Invalid ";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { }; 
}
