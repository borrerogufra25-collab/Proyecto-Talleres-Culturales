package com.salesianos.dam.talleresculturales.controller;

import com.salesianos.dam.talleresculturales.dto.ParticipanteEditDTO;
import com.salesianos.dam.talleresculturales.dto.ParticipanteSimpleDTO;
import com.salesianos.dam.talleresculturales.model.Participante;
import com.salesianos.dam.talleresculturales.service.ParticipanteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

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
@RequestMapping("/api/v1/participantes")
@RequiredArgsConstructor
@Tag(name = "Participantes", description = "Alta y consulta de participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @Operation(summary = "Listar participantes")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de participantes (puede estar vacía)",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = ParticipanteSimpleDTO.class)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<ParticipanteSimpleDTO>> getAllParticipantes() {

        List<Participante> resultado = participanteService.getAllParticipantes();

        return ResponseEntity.ok(
                resultado.stream()
                        .map(ParticipanteSimpleDTO::of)
                        .toList()
        );

    }

    @Operation(summary = "Obtener un participante por id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Participante encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ParticipanteSimpleDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "El participante no existe",
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
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteSimpleDTO> getParticipanteById(
            @PathVariable Long id) {
        Participante resultado = participanteService.getParticipanteById(id);

        return ResponseEntity.ok(
                ParticipanteSimpleDTO.of(resultado)
        );
    }

    @Operation(summary = "Crear un participante")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Participante creado (cabecera Location con su URL)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ParticipanteSimpleDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = VALIDACION)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<ParticipanteSimpleDTO> addParticipante(
            @Valid @RequestBody ParticipanteEditDTO dto) {

        Participante creado = participanteService.addParticipante(dto.to());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(ParticipanteSimpleDTO.of(creado));
    }

    @Operation(summary = "Modificar un participante")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Participante modificado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ParticipanteSimpleDTO.class))),
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
                    description = "El participante no existe",
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
    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteSimpleDTO> updateParticipante(
            @PathVariable Long id,
            @Valid @RequestBody ParticipanteEditDTO dto) {

        Participante actualizado = participanteService.updateParticipante(id, dto.to());

        return ResponseEntity.ok(
                ParticipanteSimpleDTO.of(actualizado));

    }

    @Operation(summary = "Eliminar un participante",
            description = "No se puede eliminar un participante que esté inscrito en algún taller.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Participante eliminado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "El participante no existe",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = PARTICIPANTE_NO_ENCONTRADO))),
            @ApiResponse(
                    responseCode = "409",
                    description = "El participante está inscrito en algún taller",
                    content = @Content(
                            mediaType = PROBLEM_JSON,
                            schema = @Schema(
                                    implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = CONFLICTO_INTEGRIDAD)
                    )
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipante(@PathVariable Long id) {

        participanteService.deleteParticipante(id);
        return ResponseEntity.noContent().build();
    }
}
