package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Taller;

import java.time.LocalDateTime;
import java.util.List;

public record TallerResponseDTO(
        Long id,
        String titulo,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        OrganizadorSimpleDTO organizador,
        List<ParticipanteSimpleDTO> participantes
) {

    public static TallerResponseDTO of(Taller taller) {

        if (taller == null) {
            return null;
        }

        return new TallerResponseDTO(
                taller.getId(),
                taller.getTitulo(),
                taller.getFechaHoraInicio(),
                taller.getFechaHoraFin(),
                OrganizadorSimpleDTO.of(taller.getOrganizador()),
                taller.getParticipantes().stream()
                        .map(ParticipanteSimpleDTO::of)
                        .toList()
        );

    }

}
