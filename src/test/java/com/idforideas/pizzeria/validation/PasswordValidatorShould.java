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
public class PasswordValidatorShould {

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9c"}) // Arrange
    @DisplayName("return false when argument has short length")
    void shortLength(String password) {

        // Act
        boolean isShortLength = PasswordValidator.isShortLength(password);
        boolean isValid = isValid(password);

        // Assert
        assertTrue(isShortLength);
        assertFalse(isValid);
    }

    @ParameterizedTest
    @DisplayName("return false when argument is blank or empty")
    @ValueSource(strings = {"        ", "",}) // Arrange
    void blankOrEmpty(String password) {

        // Act
        boolean isNullOrBlankOrEmpty = PasswordValidator.isNullOrBlankOrEmpty(password);
        boolean isValid = isValid(password);

        // Assert
        assertTrue(isNullOrBlankOrEmpty);
        assertFalse(isValid);
    }

    @ParameterizedTest
    @ValueSource(strings = {"mnen@9chdp"}) // Arrange
    @DisplayName("return false when argument has not uppercase char")
    void notUppercase(String password) {

        // Act
        boolean containsUppercase = PasswordValidator.containsThis(password, UPPERCASE);
        boolean isValid = isValid(password);

        // Assert 
        assertFalse(containsUppercase);
        assertFalse(isValid);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNEN@9CHDP"}) // Arrange
    @DisplayName("return false when argument has not lowercase char")
    void notLowercase(String password) {

        // Act
        boolean containsLowercase = PasswordValidator.containsThis(password, LOWERCASE);
        boolean isValid = isValid(password);

        // Assert 
        assertFalse(containsLowercase);
        assertFalse(isValid);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9 chdP"}) // Arrange
    @DisplayName("return false when argument has space char")
    void spaceChar(String password) {

        // Act
        boolean containsSpaceChar= PasswordValidator.containsThis(password, SPACE_CHAR);
        boolean isValid = isValid(password);

        // Assert 
        assertTrue(containsSpaceChar);
        assertFalse(isValid);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"MNeN@gchdP"}) // Arrange
    @DisplayName("return false when argument has not digit")
    void notDigit(String password) {

         // Act
         boolean containsDigit = PasswordValidator.containsThis(password, DIGIT);
         boolean isValid = isValid(password);
 
         // Assert 
         assertFalse(containsDigit);
         assertFalse(isValid);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeNa9chdP"}) // Arrange
    @DisplayName("return false when argument has not especial char")
    void notEspecialChar(String password) {

         // Act
         boolean containsEspecialCharacters = PasswordValidator.containsEspecialCharacters(password);
         boolean isValid = isValid(password);
 
         // Assert 
         assertFalse(containsEspecialCharacters);
         assertFalse(isValid);
    }

    @Test
    @DisplayName("return false when argument is null")
    void isNull() {
        // Arrange
        String password = null;

        // Act
        boolean isNullOrBlankOrEmpty = PasswordValidator.isNullOrBlankOrEmpty(password);
        boolean isValid = isValid(password);

        // Assert
        assertTrue(isNullOrBlankOrEmpty);
        assertFalse(isValid);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MNeN@9chdP"}) // Arrange
    @DisplayName("return true when argument pass all tests")
    void allTests(String password) {

        // Act
        boolean isValid = isValid(password);

        // Assert
        assertTrue(isValid);
    }

    private boolean isValid(String password) {
        return new PasswordValidator().isValid(password, null);
    }
}
