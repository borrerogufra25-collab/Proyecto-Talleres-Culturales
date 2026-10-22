package com.salesianos.dam.talleresculturales.exception;

import lombok.Getter;

@Getter
public class ParticipanteYaInscritoException extends RuntimeException {

    private final String nombreCompleto;
    private final String tituloTaller;


    public ParticipanteYaInscritoException(String nombreCompleto, String tituloTaller) {
        super("El participante " + nombreCompleto + " ya está inscrito en el taller " + tituloTaller);
        this.nombreCompleto = nombreCompleto;
        this.tituloTaller = tituloTaller;
    }
}
