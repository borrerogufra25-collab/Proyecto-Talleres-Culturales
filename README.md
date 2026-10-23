# Talleres Culturales API

API REST para gestionar **organizadores**, **talleres** y **participantes**, incluyendo las **inscripciones** de participantes en talleres (relación muchos a muchos).

1º proyecto de _Acceso a Datos_ y _Programación de Servicios y Procesos_ · Curso 2026-27.

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-blue)
![H2](https://img.shields.io/badge/BD-H2%20en%20memoria-lightgrey)

---

## Índice

1. [Características](#-características)
2. [Tecnologías](#-tecnologías)
3. [Requisitos previos](#-requisitos-previos)
4. [Puesta en marcha](#-puesta-en-marcha)
5. [Modelo de datos](#-modelo-de-datos)
6. [Endpoints](#-endpoints)
7. [Ejemplos de uso](#-ejemplos-de-uso)
8. [Validación](#-validación)
9. [Gestión de errores](#-gestión-de-errores)
10. [Internacionalización](#-internacionalización)
11. [Documentación con Swagger](#-documentación-con-swagger)
12. [Colección de Postman](#-colección-de-postman)
13. [Estructura del proyecto](#-estructura-del-proyecto)
14. [Decisiones de diseño](#-decisiones-de-diseño)

---

## Características

- CRUD completo de **Organizadores**, **Talleres** y **Participantes**.
- Alta, consulta, modificación y borrado de **Participantes**.
- Gestión de **inscripciones** (inscribir, listar inscritos y cancelar inscripción).
- Uso de **DTOs** para no exponer las entidades y evitar referencias circulares.
- **Validación** de entradas con Jakarta Validation, incluida una **anotación personalizada** (`@FechasValidas`).
- Errores unificados en formato **`ProblemDetail`** (RFC 9457).
- Mensajes de validación y error **externalizados** y disponibles en **español e inglés**.
- Documentación interactiva con **OpenAPI 3 + Swagger UI**, con ejemplos de datos.
- Datos de prueba cargados automáticamente al arrancar.

## Tecnologías

| Tecnología                  | Uso                                                           |
| --------------------------- | ------------------------------------------------------------- |
| Java 25                     | Lenguaje                                                      |
| Spring Boot 4.1.1           | Framework (Web MVC, Data JPA, Validation)                     |
| Spring Data JPA / Hibernate | Persistencia                                                  |
| H2                          | Base de datos en memoria                                      |
| Lombok                      | Reducción de código repetitivo en las entidades y excepciones |
| springdoc-openapi 3.0.3     | Generación de OpenAPI + Swagger UI                            |
| Maven (wrapper incluido)    | Gestión de dependencias y construcción                        |

## Requisitos previos

- **JDK 25** instalado y configurado.
- No hace falta instalar Maven: el proyecto incluye el wrapper (`mvnw` / `mvnw.cmd`).
- No hace falta instalar ninguna base de datos: se usa H2 en memoria.

## Puesta en marcha

```bash
# 1. Clonar el repositorio
git clone https://github.com/borrerogufra25-collab/Proyecto-Talleres-Culturales.git
cd Talleres-Culturales

# 2. Arrancar la aplicación
./mvnw spring-boot:run          # Linux / macOS
mvnw.cmd spring-boot:run        # Windows
```

La API quedará disponible en **http://localhost:8080**.

### Datos de prueba

Al arrancar se cargan automáticamente (`DataSeed`):

| Entidad       | Datos                                                                                                                                |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| Organizadores | Ana López, Luis Martín                                                                                                               |
| Participantes | María García Pérez, Carlos Ruiz Soto, Lucía Fernández Gil                                                                            |
| Talleres      | **Cerámica** (Ana · 2 inscritos), **Fotografía** (Luis · 1 inscrito), **Teatro** (Ana · _sin inscritos_, para probar la lista vacía) |

Las fechas de los talleres de prueba se generan a partir de la fecha actual (a partir de dentro de 7 días), de modo que siempre cumplen la validación de fecha futura.

### Consola de H2

Con la aplicación en marcha: **http://localhost:8080/h2-console**

| Campo    | Valor                  |
| -------- | ---------------------- |
| JDBC URL | `jdbc:h2:mem:talleres` |
| User     | `sa`                   |
| Password | _(vacía)_              |

> ⚠️ La base de datos es en memoria (`create-drop`): **los datos se pierden al parar la aplicación**.

## Modelo de datos

- **Organizador → Taller (1:N):** un organizador puede organizar muchos talleres y cada taller tiene un único organizador (obligatorio).
- **Taller ↔ Participante (N:M):** se resuelve con la tabla intermedia `inscripcion` (`taller_id`, `participante_id`), sin atributos adicionales ni cuarta entidad de dominio. El lado propietario de la relación es `Taller`.
- Un taller recién creado tiene una **lista de participantes vacía**.
- El `titulo` del taller es **único**.

## Endpoints

Ruta base: `/api/v1`

### Organizadores · `/api/v1/organizadores`

| Método   | Ruta                  | Descripción              | Respuestas                           |
| -------- | --------------------- | ------------------------ | ------------------------------------ |
| `GET`    | `/organizadores`      | Listar organizadores     | `200`                                |
| `GET`    | `/organizadores/{id}` | Obtener un organizador   | `200`, `404`                         |
| `POST`   | `/organizadores`      | Crear un organizador     | `201`, `400`                         |
| `PUT`    | `/organizadores/{id}` | Modificar un organizador | `200`, `400`, `404`                  |
| `DELETE` | `/organizadores/{id}` | Eliminar un organizador  | `204`, `404`, `409` (tiene talleres) |

### Talleres · `/api/v1/talleres`

| Método   | Ruta             | Descripción         | Respuestas                                                 |
| -------- | ---------------- | ------------------- | ---------------------------------------------------------- |
| `GET`    | `/talleres`      | Listar talleres     | `200`                                                      |
| `GET`    | `/talleres/{id}` | Obtener un taller   | `200`, `404`                                               |
| `POST`   | `/talleres`      | Crear un taller     | `201`, `400`, `404` (organizador), `409` (título repetido) |
| `PUT`    | `/talleres/{id}` | Modificar un taller | `200`, `400`, `404`, `409`                                 |
| `DELETE` | `/talleres/{id}` | Eliminar un taller  | `204`, `404`                                               |

### Participantes · `/api/v1/participantes`

| Método   | Ruta                  | Descripción               | Respuestas                                |
| -------- | --------------------- | ------------------------- | ----------------------------------------- |
| `GET`    | `/participantes`      | Listar participantes      | `200`                                     |
| `GET`    | `/participantes/{id}` | Obtener un participante   | `200`, `404`                              |
| `POST`   | `/participantes`      | Crear un participante     | `201`, `400`                              |
| `PUT`    | `/participantes/{id}` | Modificar un participante | `200`, `400`, `404`                       |
| `DELETE` | `/participantes/{id}` | Eliminar un participante  | `204`, `404`, `409` (tiene inscripciones) |

### Inscripciones (N:M) · `/api/v1/talleres/{tallerId}/participantes`

| Método   | Ruta                                                  | Descripción                                                        | Respuestas                        |
| -------- | ----------------------------------------------------- | ------------------------------------------------------------------ | --------------------------------- |
| `POST`   | `/talleres/{tallerId}/participantes/{participanteId}` | Inscribir un participante existente en un taller existente         | `200`, `404`, `409` (ya inscrito) |
| `GET`    | `/talleres/{tallerId}/participantes`                  | Listar los participantes inscritos (lista vacía si no hay ninguno) | `200`, `404`                      |
| `DELETE` | `/talleres/{tallerId}/participantes/{participanteId}` | Cancelar la inscripción (el taller y el participante se conservan) | `204`, `404`                      |

**Comportamiento a tener en cuenta:**

- No se permite repetir la misma inscripción → `409 Conflict`.
- Si el taller existe pero no tiene inscritos, el `GET` devuelve `[]` (nunca `null`).
- Si taller y participante existen pero **no están vinculados**, el `DELETE` finaliza sin cambios y devuelve `204`.
- Si el taller o el participante **no existen**, se devuelve `404`.

## Ejemplos de uso

### Crear un organizador

```http
POST /api/v1/organizadores
Content-Type: application/json

{
  "nombre": "Ana López",
  "email": "ana@cultura.es"
}
```

`201 Created` (con cabecera `Location`):

```json
{
  "id": 3,
  "nombre": "Ana López",
  "email": "ana@cultura.es"
}
```

### Crear un taller

```http
POST /api/v1/talleres
Content-Type: application/json

{
  "titulo": "Pintura al óleo",
  "fechaHoraInicio": "2027-01-15T10:00:00",
  "fechaHoraFin": "2027-01-15T12:00:00",
  "organizadorId": 1
}
```

`201 Created`:

```json
{
  "id": 4,
  "titulo": "Pintura al óleo",
  "fechaHoraInicio": "2027-01-15T10:00:00",
  "fechaHoraFin": "2027-01-15T12:00:00",
  "organizador": {
    "id": 1,
    "nombre": "Ana López",
    "email": "ana@cultura.es"
  },
  "participantes": []
}
```

### Inscribir un participante

```http
POST /api/v1/talleres/4/participantes/1
```

`200 OK`: devuelve el taller con el participante ya incluido en `participantes`.

### Error: participante ya inscrito

Repetir la petición anterior devuelve `409 Conflict`:

```json
{
  "type": "about:blank",
  "title": "Conflict",
  "status": 409,
  "detail": "El participante María García Pérez ya está inscrito en el taller Pintura al óleo",
  "instance": "/api/v1/talleres/4/participantes/1"
}
```

## Validación

Se valida con **Jakarta Validation** sobre los DTOs de entrada (`@Valid` en los controladores).

| DTO                   | Campo              | Reglas                               |
| --------------------- | ------------------ | ------------------------------------ |
| `OrganizadorEditDTO`  | `nombre`           | `@NotBlank`                          |
|                       | `email`            | `@NotBlank`, `@Email`                |
| `ParticipanteEditDTO` | `nombreCompleto`   | `@NotBlank`                          |
| `TallerEditDTO`       | `titulo`           | `@NotBlank`                          |
|                       | `fechaHoraInicio`  | `@NotNull`, `@FutureOrPresent`       |
|                       | `fechaHoraFin`     | `@NotNull`, `@FutureOrPresent`       |
|                       | `organizadorId`    | `@NotNull`                           |
|                       | _(clase completa)_ | **`@FechasValidas`** (personalizada) |

### Anotación personalizada: `@FechasValidas`

Valida a nivel de clase que `fechaHoraFin` sea **estrictamente posterior** a `fechaHoraInicio`.

- `validation/FechasValidas.java`: define la anotación.
- `validation/FechasValidasValidator.java`: implementa `ConstraintValidator`. Si alguna de las dos fechas es `null`, devuelve `true` para no duplicar el error, que ya lo gestiona `@NotNull`.

### Unicidad del título del taller

Se comprueba en el servicio (no con anotaciones) ignorando **mayúsculas/minúsculas** y **espacios al principio y al final**:

- Al **crear**: `existsByTituloIgnoreCase(titulo.trim())`.
- Al **modificar**: `existsByTituloIgnoreCaseAndIdNot(titulo.trim(), id)`, de modo que conservar el propio título no genera error.

## Gestión de errores

Todos los errores se devuelven como **`ProblemDetail`** (`application/problem+json`) gracias a un único `@RestControllerAdvice` (`GlobalExceptionHandler`).

### Excepciones propias

| Excepción                          | HTTP            | Cuándo se lanza                                         |
| ---------------------------------- | --------------- | ------------------------------------------------------- |
| `OrganizadorNoEncontradoException` | `404 Not Found` | El organizador no existe                                |
| `TallerNoEncontradoException`      | `404 Not Found` | El taller no existe                                     |
| `ParticipanteNoEncontrado`         | `404 Not Found` | El participante no existe                               |
| `TallerYaExisteException`          | `409 Conflict`  | Se crea o modifica un taller con el título de otro      |
| `ParticipanteYaInscritoException`  | `409 Conflict`  | Se inscribe a un participante ya inscrito en ese taller |

### Otros errores controlados

| Excepción                             | HTTP              | Cuándo ocurre                                                                             |
| ------------------------------------- | ----------------- | ----------------------------------------------------------------------------------------- |
| `MethodArgumentNotValidException`     | `400 Bad Request` | Falla alguna validación del cuerpo (se añade la lista `errores`)                          |
| `HttpMessageNotReadableException`     | `400 Bad Request` | JSON mal formado o fechas con formato incorrecto                                          |
| `MethodArgumentTypeMismatchException` | `400 Bad Request` | Un parámetro de ruta con tipo inválido (p. ej. `/talleres/abc`)                           |
| `DataIntegrityViolationException`     | `409 Conflict`    | Se borra un recurso con datos asociados (organizador con talleres, participante inscrito) |

> **¿Por qué 409 y no 400?** Los dos casos de negocio (título duplicado y participante ya inscrito) son peticiones bien formadas que **entran en conflicto con el estado actual** del servidor, que es justo lo que representa `409 Conflict`.

### Ejemplo de error de validación

```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "La petición contiene datos no válidos",
  "instance": "/api/v1/talleres",
  "errores": [
    {
      "campo": "titulo",
      "mensaje": "El título del taller no puede estar vacío"
    },
    {
      "campo": "tallerEditDTO",
      "mensaje": "La fecha de fin debe ser posterior a la de inicio"
    }
  ]
}
```

## Internacionalización

Ningún mensaje está escrito directamente en las anotaciones ni en los controladores: todos se externalizan en ficheros `properties`.

| Fichero                                     | Idioma                |
| ------------------------------------------- | --------------------- |
| `src/main/resources/messages.properties`    | Español (por defecto) |
| `src/main/resources/messages_en.properties` | Inglés                |

- Las anotaciones referencian las claves, por ejemplo `@NotBlank(message = "{taller.titulo.notblank}")`.
- `ValidationConfig` conecta el `MessageSource` con el validador (`LocalValidatorFactoryBean`) para que los mensajes de validación también se traduzcan.
- El `GlobalExceptionHandler` resuelve los mensajes de error con el `Locale` de la petición, y admite parámetros (`{0}`, `{1}`).
- El idioma se elige con la cabecera **`Accept-Language`**. Si no se envía o el idioma no está soportado, se usa español (`spring.messages.fallback-to-system-locale=false`).

```http
GET /api/v1/talleres/99
Accept-Language: en
```

```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "The workshop with ID 99 was not found",
  "instance": "/api/v1/talleres/99"
}
```

## Documentación con Swagger

Con la aplicación en marcha:

| Recurso                           | URL                                   |
| --------------------------------- | ------------------------------------- |
| Swagger UI (interfaz interactiva) | http://localhost:8080/swagger-ui.html |
| Especificación OpenAPI (JSON)     | http://localhost:8080/v3/api-docs     |

Todos los endpoints están documentados con `@Operation`, `@ApiResponse` y **ejemplos de datos** (`@ExampleObject`, centralizados en `docs/ApiExamples.java`). Los DTOs incluyen `@Schema` con descripción y ejemplo de cada campo.

## Colección de Postman

**Cómo usarla:**

1. Arranca la aplicación (`./mvnw spring-boot:run`).
2. En Postman: **Import → Upload Files** y selecciona el `.json` de la colección.
3. Las peticiones apuntan a `http://localhost:8080`.

Gracias al `DataSeed`, los ids `1` y `2` (organizadores), `1` a `3` (participantes) y `1` a `3` (talleres) existen al arrancar, así que las peticiones de ejemplo funcionan directamente.

## Decisiones de diseño

- **DTOs como `record`:** las entidades nunca se exponen. Los DTOs de entrada (`*EditDTO`) llevan las validaciones y un método `to()` que construye la entidad; los de salida tienen un método estático `of()` que la convierte.
- **Sin referencias circulares:** `Taller` apunta a `Organizador` y `Participante`, pero ni `Organizador` ni `Participante` conocen a `Taller`. `TallerResponseDTO` usa `OrganizadorSimpleDTO` y `List<ParticipanteSimpleDTO>`.
- **Colecciones vacías seguras:** `participantes` se inicializa con `@Builder.Default` y `new ArrayList<>()`, por lo que nunca es `null` y la respuesta JSON es `[]`.
- **Servicios que reutilizan servicios:** `TallerService` usa `OrganizadorService` y `ParticipanteService` para localizar entidades, de modo que el `404` se genera siempre en un único sitio.
- **Códigos HTTP coherentes:** `201` + cabecera `Location` al crear, `200` al consultar o modificar, `204` al borrar, `404` si no existe y `409` ante conflictos de negocio.
- **Mensajes externalizados:** cero textos hardcodeados en anotaciones; todo en `messages*.properties`.

---

**Autor/a:** _Francisco Borrero Guerrero_ · Proyecto AD-PSP 2026-27
