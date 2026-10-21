package com.salesianos.dam.talleresculturales.controller;

import com.salesianos.dam.talleresculturales.dto.OrganizadorSimpleDTO;
import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.repository.OrganizadorRepository;
import com.salesianos.dam.talleresculturales.service.OrganizadorService;
import java.util.List;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizadores")
@RequiredArgsConstructor
public class OrganizadorController {

  private final OrganizadorRepository organizadorRepository;
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
  public ResponseEntity<List<OrganizadorSimpleDTO>> getOrganizadorById(
      @PathVariable Long id) {

    List<Organizador> resultado = organizadorService.getOrganizadorById(id);

    return ResponseEntity.ok(
        resultado.stream()
            .map(OrganizadorSimpleDTO::of)
            .toList()
    );

  }

}
