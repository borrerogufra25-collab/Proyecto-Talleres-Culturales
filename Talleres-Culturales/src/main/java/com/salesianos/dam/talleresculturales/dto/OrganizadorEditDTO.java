package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OrganizadorEditDTO(
        @NotBlank(message = "{organizador.nombre.notblank}")
        String nombre,

        @NotBlank(message = "{organizador.email.notblank}")
        @Email(message = "{organizador.email.email}")
        String email
) {

    public Organizador to() {
        return Organizador.builder()
                .nombre(nombre)
                .email(email)
                .build();
    }

}
