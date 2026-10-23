package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Taller;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Taller con su organizador y los participantes inscritos")
public record TallerResponseDTO(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Cerámica")
        String titulo,

        @Schema(example = "2027-01-15T10:00:00")
        LocalDateTime fechaHoraInicio,

        @Schema(example = "2027-01-15T12:00:00")
        LocalDateTime fechaHoraFin,

        OrganizadorSimpleDTO organizador,

        @Schema(description = "Participantes inscritos (lista vacía si no hay ninguno)")
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
