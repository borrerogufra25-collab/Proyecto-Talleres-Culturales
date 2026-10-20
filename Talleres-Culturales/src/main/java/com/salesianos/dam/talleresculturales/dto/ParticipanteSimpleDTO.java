package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Participante;

public record ParticipanteSimpleDTO(
    String nombreCompleto
) {

  public static ParticipanteSimpleDTO of(Participante participante) {

    if (participante == null) {
      return null;
    }

    return new ParticipanteSimpleDTO(
        participante.getNombreCompleto()
    );

  }

}
