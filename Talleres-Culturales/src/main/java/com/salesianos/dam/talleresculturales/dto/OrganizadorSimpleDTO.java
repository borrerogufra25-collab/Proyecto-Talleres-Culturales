package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Vista resumida de un organizador")
public record OrganizadorSimpleDTO(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Ana López")
        String nombre,

        @Schema(example = "ana@cultura.es")
        String email
) {

    public static OrganizadorSimpleDTO of(Organizador organizador) {

        if (organizador == null) {
            return null;
        }

        return new OrganizadorSimpleDTO(
                organizador.getId(),
                organizador.getNombre(),
                organizador.getEmail()
        );

    }
}
