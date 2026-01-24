package com.jatramaya.bibliotech.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameStrengthValidator implements ConstraintValidator<StrongUsername, String> {
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return false;
        }
        
        boolean hasDigit = username.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = username.chars().anyMatch(ch -> 
            "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0
        );
        
        return hasDigit && hasSpecial;
    }
}
