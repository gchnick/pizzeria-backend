package com.idforideas.pizzeria.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.idforideas.pizzeria.validation.PasswordValidator.DIGIT;
import static com.idforideas.pizzeria.validation.PasswordValidator.LOWERCASE;
import static com.idforideas.pizzeria.validation.PasswordValidator.SPACE_CHAR;
import static com.idforideas.pizzeria.validation.PasswordValidator.UPPERCASE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Password validator should")
public class PasswordValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9c"})
    @DisplayName("return false when argument has short length")
    void shortLength(String password) {
        assertTrue(PasswordValidator.isShortLength(password));
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @DisplayName("return false when argument is blank or empty")
    @ValueSource(strings = {"        ", "",})
    void blankOrEmpty(String password) {
        assertTrue(PasswordValidator.isNullOrBlankOrEmpty(password));
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mnen@9chdp"})
    @DisplayName("return false when argument has not uppercase char")
    void notUppercase(String password) {
        assertFalse(PasswordValidator.containsThis(password, UPPERCASE));
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNEN@9CHDP"})
    @DisplayName("return false when argument has not lowercase char")
    void notLowercase(String password) {
        assertFalse(PasswordValidator.containsThis(password, LOWERCASE));
        assertFalse(isValid(password));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9 chdP"})
    @DisplayName("return false when argument has space char")
    void spaceChar(String password) {
        assertTrue(PasswordValidator.containsThis(password, SPACE_CHAR));
        assertFalse(isValid(password));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"MNeN@gchdP"})
    @DisplayName("return false when argument has not digit")
    void notDigit(String password) {
        assertFalse(PasswordValidator.containsThis(password, DIGIT));
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeNa9chdP"})
    @DisplayName("return false when argument has not especial char")
    void notEspecialChar(String password) {
        assertFalse(PasswordValidator.containsEspecialCharacters(password));
        assertFalse(isValid(password));
    }

    @Test
    @DisplayName("return false when argument is null")
    void isNull() {
        String password = null;
        assertTrue(PasswordValidator.isNullOrBlankOrEmpty(password));
        assertFalse(isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9chdP"})
    @DisplayName("return true when argument pass all tests")
    void allTests(String password) {
        assertTrue(isValid(password));
    }

    private boolean isValid(String password) {
        return new PasswordValidator().isValid(password, null);
    }
}
