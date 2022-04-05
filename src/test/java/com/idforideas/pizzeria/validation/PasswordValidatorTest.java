package com.idforideas.pizzeria.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();
   
    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9c","MNEN@9CHDP", "mnen@9chdp", "MNeN@gchdP",
                             "MNeNa9chdP", "        ", "", "MNeN@9 chdP"})
    void whenPasswordIsInvalid(String password) {
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9chdP"})
    void whenPasswordIsValid(String password) {
        assertTrue(isValid(password));
    }

    @Test
    void whenPasswordIsNull() {
        assertFalse(isValid(null));
    }

    private boolean isValid(String arg) {
        return validator.isValid(arg, null);
    }
}
