package com.salesianos.dam.talleresculturales.controller;

import com.salesianos.dam.talleresculturales.dto.ParticipanteEditDTO;
import com.salesianos.dam.talleresculturales.dto.ParticipanteSimpleDTO;
import com.salesianos.dam.talleresculturales.model.Participante;
import com.salesianos.dam.talleresculturales.service.ParticipanteService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
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
@RequestMapping("/api/v1/participantes")
@RequiredArgsConstructor
public class ParticipanteController {

  private final ParticipanteService participanteService;

  @GetMapping
  public ResponseEntity<List<ParticipanteSimpleDTO>> getAllParticipantes() {

    List<Participante> resultado = participanteService.getAllParticipantes();

    return ResponseEntity.ok(
        resultado.stream()
            .map(ParticipanteSimpleDTO::of)
            .toList()
    );

  }

  @GetMapping("/{id}")
  public ResponseEntity<ParticipanteSimpleDTO> getParticipanteById(
      @PathVariable Long id) {
    Participante resultado = participanteService.getParticipanteById(id);

    return ResponseEntity.ok(
        ParticipanteSimpleDTO.of(resultado)
    );
  }

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

  @PutMapping("/{id")
  public ResponseEntity<ParticipanteSimpleDTO> updateOrganizador(
      @PathVariable Long id,
      @Valid @RequestBody ParticipanteEditDTO dto) {

    Participante actualizado = participanteService.updateParticipante(id, dto.to());

    return ResponseEntity.ok(
        ParticipanteSimpleDTO.of(actualizado));

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteParticipante(@PathVariable Long id) {

    participanteService.deleteParticipante(id);
    return ResponseEntity.noContent().build();
  }
}
