package com.salesianos.dam.talleresculturales.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(ParticipanteNoEncontrado.class)
    public ProblemDetail handleParticipanteNoEncontrado(
            ParticipanteNoEncontrado ex, Locale locale) {
        String detalle = messageSource.getMessage(
                "participante.noencontrado",
                new Object[]{ex.getId()},
                locale);

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detalle);
    }

    @ExceptionHandler(ParticipanteYaInscritoException.class)
    public ProblemDetail handleParticipanteYaInscrito(
            ParticipanteYaInscritoException ex, Locale locale) {
        String detalle = messageSource.getMessage(
                "participante.yainscrito",
                new Object[]{ex.getNombreCompleto(), ex.getTituloTaller()},
                locale);

        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, detalle);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidacion(
            MethodArgumentNotValidException ex, Locale locale) {

        String detalle = messageSource.getMessage(
                "validacion.fallida",
                null,
                locale);

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detalle);

        List<Map<String, String>> errores = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.add(Map.of(
                    "campo", error.getField(),
                    "mensaje", Objects.requireNonNullElse(error.getDefaultMessage(), "")));
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errores.add(Map.of(
                    "campo", error.getObjectName(),
                    "mensaje", Objects.requireNonNullElse(error.getDefaultMessage(), "")));
        }

        problema.setProperty("errores", errores);
        return problema;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleMensajeIlegible(
            HttpMessageNotReadableException ex, Locale locale) {

        String detalle = messageSource.getMessage(
                "peticion.ilegible",
                null,
                locale);

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detalle);
    }

}