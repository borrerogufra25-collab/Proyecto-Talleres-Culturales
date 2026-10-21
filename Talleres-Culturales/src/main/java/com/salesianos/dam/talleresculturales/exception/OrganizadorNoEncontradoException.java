package com.salesianos.dam.talleresculturales.exception;

import lombok.Getter;

@Getter
public class OrganizadorNoEncontradoException extends RuntimeException {

    private final Long id;

    public OrganizadorNoEncontradoException(Long id) {
        super("Organizador no encontrado: " + id);
        this.id = id;
    }
}