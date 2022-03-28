package com.idforideas.pizzeria.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String pwd, ConstraintValidatorContext arg1) {

        if( pwd == null || pwd.isEmpty() || pwd.isBlank()) return false;
        if( pwd.length() < 8 ) return false;
        if( !pwd.matches("[0-9]") ) return false;
        if( !pwd.matches("[a-z]") ) return false;
        if( !pwd.matches("[A-Z]") ) return false;
        if( !pwd.matches("[@#$%^&+=]") ) return false;
        if( pwd.matches("\\s") ) return false;
        
        return true;
    }
}
