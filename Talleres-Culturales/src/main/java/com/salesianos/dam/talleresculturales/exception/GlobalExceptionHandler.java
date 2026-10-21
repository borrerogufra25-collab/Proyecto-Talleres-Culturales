package com.salesianos.dam.talleresculturales.exception;

import java.util.Locale;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(OrganizadorNoEncontradoException.class)
    public ProblemDetail handleOrganizadorNoEncontrado(
            OrganizadorNoEncontradoException ex, Locale locale) {

        String detalle = messageSource.getMessage(
                "organizador.noencontrado",
                new Object[]{ex.getId()},
                locale);

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detalle);
    }

    @ExceptionHandler(TallerNoEncontradoException.class)
    public ProblemDetail handleTallerNoEncontrado(
            TallerNoEncontradoException ex, Locale locale) {
        String detalle = messageSource.getMessage(
                "taller.noencontrado",
                new Object[]{ex.getId()},
                locale);

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detalle);
    }

    @ExceptionHandler(TallerYaExisteException.class)
    public ProblemDetail handleTallerYaExiste(
            TallerYaExisteException ex, Locale locale) {
        String detalle = messageSource.getMessage(
                "taller.yaexiste",
                new Object[]{ex.getTitulo()},
                locale);

        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, detalle);
    }

}