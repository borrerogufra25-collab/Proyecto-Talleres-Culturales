package com.salesianos.dam.talleresculturales.controller;

import com.salesianos.dam.talleresculturales.dto.ParticipanteSimpleDTO;
import com.salesianos.dam.talleresculturales.dto.TallerEditDTO;
import com.salesianos.dam.talleresculturales.dto.TallerResponseDTO;
import com.salesianos.dam.talleresculturales.model.Taller;
import com.salesianos.dam.talleresculturales.service.TallerService;

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

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/talleres")
@RequiredArgsConstructor
public class TallerController {

    private final TallerService tallerService;

    @GetMapping
    public ResponseEntity<List<TallerResponseDTO>> getAllTalleres() {

        List<Taller> resultado = tallerService.getAllTalleres();

        return ResponseEntity.ok(
                resultado.stream()
                        .map(TallerResponseDTO::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TallerResponseDTO> getTallerById(@PathVariable Long id) {

        Taller resultado = tallerService.getTallerById(id);

        return ResponseEntity.ok(TallerResponseDTO.of(resultado));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<TallerResponseDTO> updateTaller(
            @PathVariable Long id,
            @Valid @RequestBody TallerEditDTO dto) {

        Taller actualizado = tallerService.updateTaller(id, dto);

        return ResponseEntity.ok(TallerResponseDTO.of(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaller(@PathVariable Long id) {

        tallerService.deleteTaller(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<TallerResponseDTO> inscribirParticipante(
            @PathVariable Long tallerId,
            @PathVariable Long participanteId) {

        Taller actualizado = tallerService.inscribirParticipante(tallerId, participanteId);

        return ResponseEntity.ok(
                TallerResponseDTO.of(actualizado));
    }

    @GetMapping("/{tallerId}/participantes")
    public ResponseEntity<List<ParticipanteSimpleDTO>> getParticipantesDeTaller(
            @PathVariable Long tallerId) {

        Taller taller = tallerService.getTallerById(tallerId);

        return ResponseEntity.ok(
                taller.getParticipantes().stream()
                        .map(ParticipanteSimpleDTO::of)
                        .toList());
    }

    @DeleteMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<Void> desinscribirParticipante(
            @PathVariable Long tallerId,
            @PathVariable Long participanteId) {

        tallerService.desinscribirParticipante(tallerId, participanteId);

        return ResponseEntity.noContent().build();
    }

}