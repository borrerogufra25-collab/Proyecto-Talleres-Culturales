package com.salesianos.dam.talleresculturales.controller;

import com.salesianos.dam.talleresculturales.dto.OrganizadorEditDTO;
import com.salesianos.dam.talleresculturales.dto.OrganizadorSimpleDTO;
import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.service.OrganizadorService;

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
@RequestMapping("/api/v1/organizadores")
@RequiredArgsConstructor
@Tag(name = "Organizadores", description = "CRUD de organizadores de talleres")
public class OrganizadorController {

    private final OrganizadorService organizadorService;

    @Operation(summary = "Listar organizadores")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de organizadores (puede estar vacía)",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = OrganizadorSimpleDTO.class))))
    @GetMapping
    public ResponseEntity<List<OrganizadorSimpleDTO>> getAllOrganizadores() {

        List<Organizador> resultado = organizadorService.getAllOrganizadores();

        return ResponseEntity.ok(
                resultado.stream()
                        .map(OrganizadorSimpleDTO::of)
                        .toList()
        );
    }

    @Operation(summary = "Obtener un organizador por id")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organizador encontrado",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrganizadorSimpleDTO.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El organizador no existe",
                            content = @Content(
                                    mediaType = PROBLEM_JSON,
                                    schema = @Schema(
                                            implementation = ProblemDetail.class),
                                    examples = @ExampleObject(
                                            value = ORGANIZADOR_NO_ENCONTRADO)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrganizadorSimpleDTO> getOrganizadorById(
            @PathVariable Long id) {

        Organizador resultado = organizadorService.getOrganizadorById(id);

        return ResponseEntity.ok(
                OrganizadorSimpleDTO.of(resultado)
        );
    }

    @Operation(summary = "Crear un organizador")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Organizador creado (cabecera Location con su URL)",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrganizadorSimpleDTO.class))),
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
    public ResponseEntity<OrganizadorSimpleDTO> addOrganizador(
            @Valid @RequestBody OrganizadorEditDTO dto) {

        Organizador creado = organizadorService.addOrganizador(dto.to());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(OrganizadorSimpleDTO.of(creado));
    }

    @Operation(summary = "Modificar un organizador")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Organizador modificado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = OrganizadorSimpleDTO.class))),
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
                                    value = ORGANIZADOR_NO_ENCONTRADO)
                    )
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<OrganizadorSimpleDTO> updateOrganizador(
            @PathVariable Long id,
            @Valid @RequestBody OrganizadorEditDTO dto) {

        Organizador actualizado = organizadorService.updateOrganizador(id, dto.to());

        return ResponseEntity.ok(
                OrganizadorSimpleDTO.of(actualizado));
    }

    @Operation(summary = "Eliminar un organizador",
            description = "No se puede eliminar un organizador que tenga talleres asociados.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Organizador eliminado"),
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
                    description = "El organizador tiene talleres asociados",
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
    public ResponseEntity<Void> deleteOrganizador(@PathVariable Long id) {
        organizadorService.deleteOrganizador(id);
        return ResponseEntity.noContent().build();
    }

}
