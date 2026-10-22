package com.salesianos.dam.talleresculturales.controller;

import com.salesianos.dam.talleresculturales.dto.ParticipanteSimpleDTO;
import com.salesianos.dam.talleresculturales.dto.TallerEditDTO;
import com.salesianos.dam.talleresculturales.dto.TallerResponseDTO;
import com.salesianos.dam.talleresculturales.model.Taller;
import com.salesianos.dam.talleresculturales.service.TallerService;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.salesianos.dam.talleresculturales.docs.ApiExamples.*;

@RestController
@RequestMapping("/api/v1/talleres")
@RequiredArgsConstructor
@Tag(name = "Talleres", description = "CRUD de talleres y gestión de inscripciones (N:M con participantes)")
public class TallerController {

    private final TallerService tallerService;

    @Operation(summary = "Listar talleres")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de talleres (puede estar vacía)",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = TallerResponseDTO.class)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<TallerResponseDTO>> getAllTalleres() {

        List<Taller> resultado = tallerService.getAllTalleres();

        return ResponseEntity.ok(
                resultado.stream()
                        .map(TallerResponseDTO::of)
                        .toList()
        );
    }

    @Operation(summary = "Obtener un taller por id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Taller encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = TallerResponseDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "El taller no existe",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = TALLER_NO_ENCONTRADO)
                    )
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TallerResponseDTO> getTallerById(@PathVariable Long id) {

        Taller resultado = tallerService.getTallerById(id);

        return ResponseEntity.ok(TallerResponseDTO.of(resultado));
    }

    @Operation(summary = "Crear un taller",
            description = "El título debe ser único (se ignoran mayúsculas/minúsculas y espacios laterales). "
                    + "Un taller nuevo no tiene participantes.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Taller creado (cabecera Location con su URL)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = TallerResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = VALIDACION))),
            @ApiResponse(
                    responseCode = "404",
                    description = "El organizador no existe",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = ORGANIZADOR_NO_ENCONTRADO))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe otro taller con ese título",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = TALLER_YA_EXISTE)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<TallerResponseDTO> addTaller(
            @Valid @RequestBody TallerEditDTO dto) {

        Taller creado = tallerService.addTaller(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(TallerResponseDTO.of(creado));
    }

    @Operation(summary = "Modificar un taller",
            description = "Conservar el propio título no genera conflicto. Los participantes inscritos no cambian.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Taller modificado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = TallerResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = VALIDACION))),
            @ApiResponse(
                    responseCode = "404",
                    description = "El taller o el organizador no existen",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = TALLER_NO_ENCONTRADO))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe otro taller con ese título",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = TALLER_YA_EXISTE)
                    )
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TallerResponseDTO> updateTaller(
            @PathVariable Long id,
            @Valid @RequestBody TallerEditDTO dto) {

        Taller actualizado = tallerService.updateTaller(id, dto);

        return ResponseEntity.ok(TallerResponseDTO.of(actualizado));
    }

    @Operation(summary = "Eliminar un taller",
            description = "Se conservan el organizador y los participantes.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Taller eliminado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "El taller no existe",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = TALLER_NO_ENCONTRADO)
                    )
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaller(@PathVariable Long id) {

        tallerService.deleteTaller(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Inscribir un participante en un taller",
            description = "Ambos deben existir. No se permite repetir la misma inscripción.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Inscripción realizada; devuelve el taller actualizado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = TallerResponseDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "El taller o el participante no existen",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = PARTICIPANTE_NO_ENCONTRADO))),
            @ApiResponse(
                    responseCode = "409",
                    description = "El participante ya estaba inscrito en el taller",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = PARTICIPANTE_YA_INSCRITO)
                    )
            )
    }
    )
    @PostMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<TallerResponseDTO> inscribirParticipante(
            @PathVariable Long tallerId,
            @PathVariable Long participanteId) {

        Taller actualizado = tallerService.inscribirParticipante(tallerId, participanteId);

        return ResponseEntity.ok(
                TallerResponseDTO.of(actualizado));
    }

    @Operation(summary = "Listar los participantes inscritos en un taller",
            description = "Si el taller existe pero no tiene inscritos, devuelve una lista vacía.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Participantes inscritos (puede ser una lista vacía)",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ParticipanteSimpleDTO.class)))),
            @ApiResponse(
                    responseCode = "404",
                    description = "El taller no existe",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = TALLER_NO_ENCONTRADO)
                    )
            )
    }
    )
    @GetMapping("/{tallerId}/participantes")
    public ResponseEntity<List<ParticipanteSimpleDTO>> getParticipantesDeTaller(
            @PathVariable Long tallerId) {

        Taller taller = tallerService.getTallerById(tallerId);

        return ResponseEntity.ok(
                taller.getParticipantes().stream()
                        .map(ParticipanteSimpleDTO::of)
                        .toList());
    }

    @Operation(summary = "Eliminar la inscripción de un participante en un taller",
            description = "Se conservan el taller y el participante. Si existen pero no están vinculados, "
                    + "la operación termina sin cambios.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Inscripción eliminada (o no existía)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "El taller o el participante no existen",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = PARTICIPANTE_NO_ENCONTRADO)
                    )
            )
    }
    )
    @DeleteMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<Void> desinscribirParticipante(
            @PathVariable Long tallerId,
            @PathVariable Long participanteId) {

        tallerService.desinscribirParticipante(tallerId, participanteId);

        return ResponseEntity.noContent().build();
    }

}
