package com.salesianos.dam.talleresculturales.controller;

import com.salesianos.dam.talleresculturales.dto.OrganizadorEditDTO;
import com.salesianos.dam.talleresculturales.dto.OrganizadorSimpleDTO;
import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.service.OrganizadorService;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

@RestController
@RequestMapping("/api/v1/organizadores")
@RequiredArgsConstructor
public class OrganizadorController {

    private final OrganizadorService organizadorService;

    @GetMapping
    public ResponseEntity<List<OrganizadorSimpleDTO>> getAllOrganizadores() {

        List<Organizador> resultado = organizadorService.getAllOrganizadores();

        return ResponseEntity.ok(
                resultado.stream()
                        .map(OrganizadorSimpleDTO::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizadorSimpleDTO> getOrganizadorById(
            @PathVariable Long id) {

        Organizador resultado = organizadorService.getOrganizadorById(id);

        return ResponseEntity.ok(
                OrganizadorSimpleDTO.of(resultado)
        );
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<OrganizadorSimpleDTO> updateOrganizador(
            @PathVariable Long id,
            @Valid @RequestBody OrganizadorEditDTO dto) {

        Organizador actualizado = organizadorService.updateOrganizador(id, dto.to());

        return ResponseEntity.ok(OrganizadorSimpleDTO.of(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizador(@PathVariable Long id) {
        organizadorService.deleteOrganizador(id);
        return ResponseEntity.noContent().build();
    }

}
