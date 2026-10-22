package com.salesianos.dam.talleresculturales.exception;

import lombok.Getter;

@Getter
public class ParticipanteNoEncontrado extends RuntimeException {

  private final Long id;

  public ParticipanteNoEncontrado(Long id) {
    super("Participante no encontrado: " + id);
    this.id = id;
  }
}
