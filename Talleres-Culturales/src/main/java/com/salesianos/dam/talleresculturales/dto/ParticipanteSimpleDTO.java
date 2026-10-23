package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Participante;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Vista resumida de un participante")
public record ParticipanteSimpleDTO(

        @Schema(example = "1")
        Long id,

        @Schema(example = "María García Pérez")
        String nombreCompleto
) {

    public static ParticipanteSimpleDTO of(Participante participante) {

        if (participante == null) {
            return null;
        }

        return new ParticipanteSimpleDTO(
                participante.getId(),
                participante.getNombreCompleto()
        );

    }

}
