package com.salesianos.dam.talleresculturales.validation;

import com.salesianos.dam.talleresculturales.dto.TallerEditDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechasValidasValidator implements ConstraintValidator<FechasValidas, TallerEditDTO> {

    @Override
    public boolean isValid(TallerEditDTO dto, ConstraintValidatorContext context) {
        if (dto.fechaHoraInicio() == null || dto.fechaHoraFin() == null) {
            return true;
        }
        return dto.fechaHoraFin().isAfter(dto.fechaHoraInicio());
    }
}