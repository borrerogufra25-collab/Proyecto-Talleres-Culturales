package com.salesianos.dam.talleresculturales.docs;

/**
 * Ejemplos JSON reutilizables en la documentación OpenAPI (@ExampleObject).
 */
public final class ApiExamples {

    private ApiExamples() {
    }

    public static final String PROBLEM_JSON = "application/problem+json";

    public static final String ORGANIZADOR_NO_ENCONTRADO = """
            {
              "type": "about:blank",
              "title": "Not Found",
              "status": 404,
              "detail": "No se ha encontrado el organizador con id 99",
              "instance": "/api/v1/organizadores/99"
            }""";

    public static final String TALLER_NO_ENCONTRADO = """
            {
              "type": "about:blank",
              "title": "Not Found",
              "status": 404,
              "detail": "No se ha encontrado el taller con id 99",
              "instance": "/api/v1/talleres/99"
            }""";

    public static final String PARTICIPANTE_NO_ENCONTRADO = """
            {
              "type": "about:blank",
              "title": "Not Found",
              "status": 404,
              "detail": "No se ha encontrado el participante con id 99",
              "instance": "/api/v1/participantes/99"
            }""";

    public static final String TALLER_YA_EXISTE = """
            {
              "type": "about:blank",
              "title": "Conflict",
              "status": 409,
              "detail": "Ya existe un taller con el título «Cerámica»",
              "instance": "/api/v1/talleres"
            }""";

    public static final String PARTICIPANTE_YA_INSCRITO = """
            {
              "type": "about:blank",
              "title": "Conflict",
              "status": 409,
              "detail": "El participante María García Pérez ya está inscrito en el taller Cerámica",
              "instance": "/api/v1/talleres/1/participantes/1"
            }""";

    public static final String VALIDACION = """
            {
              "type": "about:blank",
              "title": "Bad Request",
              "status": 400,
              "detail": "La petición contiene datos no válidos",
              "instance": "/api/v1/talleres",
              "errores": [
                { "campo": "titulo", "mensaje": "El título del taller no puede estar vacío" },
                { "campo": "tallerEditDTO", "mensaje": "La fecha de fin debe ser posterior a la de inicio" }
              ]
            }""";

    public static final String CONFLICTO_INTEGRIDAD = """
            {
              "type": "about:blank",
              "title": "Conflict",
              "status": 409,
              "detail": "No se puede completar la operación porque el recurso tiene datos asociados (por ejemplo, un organizador con talleres o un participante inscrito)",
              "instance": "/api/v1/organizadores/1"
            }""";

}
