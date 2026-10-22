package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos para crear o modificar un organizador")
public record OrganizadorEditDTO(

        @Schema(description = "Nombre del organizador",
                example = "Ana López")
        @NotBlank(message = "{organizador.nombre.notblank}")
        String nombre,

        @Schema(description = "Correo electrónico del organizador",
                example = "ana@cultura.es")
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
