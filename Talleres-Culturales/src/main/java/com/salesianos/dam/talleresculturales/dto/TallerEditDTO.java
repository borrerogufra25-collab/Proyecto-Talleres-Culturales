package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.model.Taller;
import com.salesianos.dam.talleresculturales.validation.FechasValidas;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@FechasValidas
public record TallerEditDTO(
        @NotBlank(message = "{taller.titulo.notblank}")
        String titulo,

        @NotNull(message = "{taller.inicio.notnull}")
        @FutureOrPresent(message = "{taller.inicio.futureorpresent}")
        LocalDateTime fechaHoraInicio,

        @NotNull(message = "{taller.fin.notnull}")
        @FutureOrPresent(message = "{taller.fin.futureorpresent}")
        LocalDateTime fechaHoraFin,

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
