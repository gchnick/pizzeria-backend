package com.idforideas.pizzeria.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String pwd, ConstraintValidatorContext arg1) {
        log.info("This is pwd {}", pwd);
        if( pwd == null || pwd.isEmpty() || pwd.isBlank()){ log.info("Pwd is blank"); return false;}
        if( pwd.length() < 8 ) { log.info("pwd.length() = {}",pwd.length()); return false;}
        if( !pwd.matches("[0-9]") ) { log.info("pwd not [0-9]"); return false;}
        if( !pwd.matches("[a-z]") ) { log.info("pwd not [a-z]"); return false;}
        if( !pwd.matches("[A-Z]") ) { log.info("pwd not [A-Z]"); return false;}
        if( !pwd.matches("[@#$%^&+=]") ) { log.info("pwd not special character"); return false;}
        //if( pwd.matches("\\s") ) return false;
        
        return true;
    }
}
