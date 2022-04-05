package com.idforideas.pizzeria.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
	private static final int MIN_LENGTH = 8;
	private static final String ESPECIAL_CHARACTERS = "@#$%^&+=";

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        if(arg0 == null || arg0.isEmpty() || arg0.isBlank()) return false;
        if(arg0.length() < MIN_LENGTH) return false;
		if(containsEspace(arg0)) return false;
        if(!containsDigits(arg0)) return false;
        if(!containsLowerCase(arg0)) return false;
        if(!containsUpperCase(arg0)) return false;
        if(!containsEspecialCharacter(arg0)) return false;

		return true;
    }
    
    private static boolean containsEspace(String arg) {
		return arg.chars().anyMatch(Character::isSpaceChar);
	}

	private static boolean containsDigits(String arg) {
		return arg.chars().anyMatch(Character::isDigit);
	}

	private static boolean containsUpperCase(String arg) {
		return arg.chars().anyMatch(Character::isUpperCase);
	}

	private static boolean containsLowerCase(String arg) {
		return arg.chars().anyMatch(Character::isLowerCase);
	}

	private static boolean containsEspecialCharacter(String arg) {
		return arg.chars().anyMatch(a -> ESPECIAL_CHARACTERS.chars().anyMatch(b -> a==b));
	}
}