package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.model.Taller;
import com.salesianos.dam.talleresculturales.validation.FechasValidas;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@FechasValidas
@Schema(description = "Datos para crear o modificar un taller")
public record TallerEditDTO(

        @Schema(description = "Título único del taller (sin distinguir mayúsculas ni espacios laterales)",
                example = "Cerámica")
        @NotBlank(message = "{taller.titulo.notblank}")
        String titulo,

        @Schema(description = "Fecha y hora de inicio (presente o futura)",
                example = "2027-01-15T10:00:00")
        @NotNull(message = "{taller.inicio.notnull}")
        @FutureOrPresent(message = "{taller.inicio.futureorpresent}")
        LocalDateTime fechaHoraInicio,

        @Schema(description = "Fecha y hora de fin (posterior al inicio)",
                example = "2027-01-15T12:00:00")
        @NotNull(message = "{taller.fin.notnull}")
        @FutureOrPresent(message = "{taller.fin.futureorpresent}")
        LocalDateTime fechaHoraFin,

        @Schema(description = "Id de un organizador existente",
                example = "1")
        @NotNull(message = "{taller.organizador.notnull}")
        Long organizadorId
) {

    public Taller to(Organizador organizador) {
        return Taller.builder()
                .titulo(titulo.trim())
                .fechaHoraInicio(fechaHoraInicio)
                .fechaHoraFin(fechaHoraFin)
                .organizador(organizador)
                .build();
    }

}
