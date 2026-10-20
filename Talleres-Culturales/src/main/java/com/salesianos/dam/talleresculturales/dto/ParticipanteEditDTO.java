package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Participante;

import jakarta.validation.constraints.NotBlank;

public record ParticipanteEditDTO(

        @NotBlank(message = "{participante.nombre.notblank}")
        String nombreCompleto
) {

    public Participante to() {
        return Participante.builder()
                .nombreCompleto(nombreCompleto)
                .build();
    }
}
