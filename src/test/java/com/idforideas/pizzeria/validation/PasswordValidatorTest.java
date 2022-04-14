package com.idforideas.pizzeria.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9c"})
    void whenItHasShortLength_thenIsInvalid(String password) {
        assertFalse(isValid(password));
        assertTrue(PasswordValidator.MIN_LENGTH > password.length());
    }

    @ParameterizedTest
    @ValueSource(strings = {"        ", "",})
    void whenItIsBlankOrEmpty_thenIsInvalid(String password) {
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mnen@9chdp"})
    void whenItHasNotUppercase_thenIsInvalid(String password) {
        assertFalse(isValid(password));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9 chdP"})
    void whenItHasSpaceChar_thenIsInvalid(String password) {
        assertFalse(isValid(password));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"MNEN@9CHDP"})
    void whenItHasNotLowercase_thenIsInvalid(String password) {
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@gchdP"})
    void whenItHasNotDigit_thenIsInvalid(String password) {
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeNa9chdP"})
    void whenItHasNotEspecialChar_thenIsInvalid(String password) {
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9chdP"})
    void whenItIsValid(String password) {
        assertTrue(isValid(password));
    }

    @Test
    void whenItIsNull_thenIsInvalid() {
        assertFalse(isValid(null));
    }


    private boolean isValid(String arg) {
        return validator.isValid(arg, null);
    }
}
