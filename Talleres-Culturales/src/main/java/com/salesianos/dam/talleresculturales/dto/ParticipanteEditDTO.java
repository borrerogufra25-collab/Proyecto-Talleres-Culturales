package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Participante;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos para crear o modificar un participante")
public record ParticipanteEditDTO(

        @Schema(description = "Nombre y apellidos del participante",
                example = "María García Pérez")
        @NotBlank(message = "{participante.nombre.notblank}")
        String nombreCompleto
) {

    public Participante to() {
        return Participante.builder()
                .nombreCompleto(nombreCompleto)
                .build();
    }
}
