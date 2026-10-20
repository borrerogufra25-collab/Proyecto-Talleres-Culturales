package com.salesianos.dam.talleresculturales.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechasValidasValidator.class)
public @interface FechasValidas {

    String message() default "{taller.fechas.validas}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
