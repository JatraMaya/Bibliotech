package com.jatramaya.bibliotech.utils.validator;

import com.jatramaya.bibliotech.dto.AddProfileDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProfileValidator implements ConstraintValidator<ValidProfileContent, AddProfileDTO> {

    @Override
    public boolean isValid(AddProfileDTO dto, ConstraintValidatorContext context) {

        if (dto == null)
            return true;

        boolean hasAvatarUrl = dto.getAvatarUrl() != null;
        boolean hasBio = dto.getBio() != null;

        return hasAvatarUrl || hasBio;

    }

}
