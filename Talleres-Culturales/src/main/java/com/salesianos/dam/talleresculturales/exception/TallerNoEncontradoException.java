package com.salesianos.dam.talleresculturales.exception;

import lombok.Getter;

@Getter
public class TallerNoEncontradoException extends RuntimeException {

    private final Long id;

    public TallerNoEncontradoException(Long id) {
        super("Taller no encontrado: " + id);
        this.id = id;
    }
}
