package com.salesianos.dam.talleresculturales.exception;

import lombok.Getter;

@Getter
public class TallerYaExisteException extends RuntimeException {

    private final String titulo;

    public TallerYaExisteException(String titulo) {
        super("El taller ya existe: " + titulo);
        this.titulo = titulo;
    }
}
