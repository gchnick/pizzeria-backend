package com.idforideas.pizzeria.validation;

import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<Password, String> {
	private static final int MIN_LENGTH = 8;
	private static final String ESPECIAL_CHARACTERS = "@#$%^&+=";

	private static final IntPredicate IS_SPACE_CHAR = Character::isSpaceChar;
	private static final IntPredicate IS_DIGIT = Character::isDigit;
	private static final IntPredicate IS_UPPERCASE = Character::isUpperCase;
	private static final IntPredicate IS_LOWERCASE = Character::isLowerCase;
	private static final BiPredicate<IntStream, IntStream> CONTAINS = (pwd, match) -> pwd.anyMatch(p -> match.anyMatch(m -> p==m));

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		IntStream pwd = arg0.chars();
		IntStream match = ESPECIAL_CHARACTERS.chars();
		
        if(arg0 == null || arg0.isEmpty() || arg0.isBlank()) return false;
        if(arg0.length() < MIN_LENGTH) return false;
		if(pwd.anyMatch(IS_SPACE_CHAR)) return false;
        if(!pwd.anyMatch(IS_DIGIT)) return false;
        if(!pwd.anyMatch(IS_UPPERCASE)) return false;
        if(!pwd.anyMatch(IS_LOWERCASE)) return false;
        if(!CONTAINS.test(pwd, match)) return false;

		return true;
    }
}