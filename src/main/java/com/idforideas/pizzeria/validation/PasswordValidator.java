package com.idforideas.pizzeria.validation;

import java.util.function.IntPredicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
	private static final int MIN_LENGTH = 8;
	private static final String ESPECIAL_CHARACTERS = "@#$%^&+=";

	public static final IntPredicate SPACE_CHAR = Character::isSpaceChar;
	public static final IntPredicate DIGIT = Character::isDigit;
	public static final IntPredicate UPPERCASE = Character::isUpperCase;
	public static final IntPredicate LOWERCASE = Character::isLowerCase;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext arg1) {
		
        if(isNullOrBlankOrEmpty(password)) return false;
        if(isShortLength(password)) return false;
        if(!containsEspecialCharacters(password)) return false;
        if(!containsThis(password, DIGIT)) return false;
        if(!containsThis(password, LOWERCASE)) return false;
        if(!containsThis(password, UPPERCASE)) return false;
		if(containsThis(password, SPACE_CHAR)) return false;
		
		return true;
    }

	public static boolean isNullOrBlankOrEmpty(String password) {
		if(password == null || password.isEmpty() || password.isBlank()) return true;
		return false;
	}

	public static boolean isShortLength(String password) {
        if(password.length() < MIN_LENGTH) return true;
		return false;
	}

	public static boolean containsThis(String password, IntPredicate mathWith) {
		return password.chars().anyMatch(mathWith);
	}

	public static boolean containsEspecialCharacters(String password) {
		return password.chars().anyMatch(a -> ESPECIAL_CHARACTERS.chars().anyMatch(b -> a==b));
	}
}