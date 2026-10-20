package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Participante;

public record ParticipanteEditDTO(
    String nombreCompleto

) {

  public Participante to() {
    return Participante.builder()
        .nombreCompleto(nombreCompleto)
        .build();
  }
}
