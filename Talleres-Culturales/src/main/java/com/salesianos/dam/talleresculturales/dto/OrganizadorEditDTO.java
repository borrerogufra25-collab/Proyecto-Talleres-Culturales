package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;

public record OrganizadorEditDTO(
    String nombre,
    String email
) {

  public Organizador to() {
    return Organizador.builder()
        .nombre(nombre)
        .email(email)
        .build();
  }

}
