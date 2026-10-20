package com.salesianos.dam.talleresculturales.dto;

import com.salesianos.dam.talleresculturales.model.Organizador;

public record OrganizadorSimpleDTO(
    String nombre,
    String email
) {

  public static OrganizadorSimpleDTO of(Organizador organizador) {

    if (organizador == null) {
      return null;
    }

    return new OrganizadorSimpleDTO(
        organizador.getNombre(),
        organizador.getEmail()
    );

  }
}
