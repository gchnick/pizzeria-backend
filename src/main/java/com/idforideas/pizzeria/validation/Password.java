package com.idforideas.pizzeria.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Constraint(validatedBy = PasswordValidator.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface Password {

    public abstract java.lang.String message() default "The password must contains at least: one digit, one lowercase and one uppercase character, one special character [@#$%^&+=]. And not contain space";
  
    public abstract  java.lang.Class<?>[] groups() default {};
  
    public abstract  java.lang.Class<? extends javax.validation.Payload>[] payload() default {};
}
