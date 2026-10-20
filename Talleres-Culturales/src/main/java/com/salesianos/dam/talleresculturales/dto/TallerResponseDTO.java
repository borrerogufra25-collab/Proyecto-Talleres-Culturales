package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.model.Participante;
import com.salesianos.dam.talleresculturales.model.Taller;
import java.time.LocalDateTime;
import java.util.List;

public record TallerResponseDTO(
    String titulo,
    LocalDateTime fechaHoraInicio,
    LocalDateTime fechaHoraFin,
    Organizador organizador,
    List<Participante> participantes
) {

  public static TallerResponseDTO of(Taller taller) {

    if (taller == null) {
      return null;
    }

    return new TallerResponseDTO(
        taller.getTitulo(),
        taller.getFechaHoraInicio(),
        taller.getFechaHoraFin(),
        taller.getOrganizador(),
        taller.getParticipantes()
    );

  }

}
