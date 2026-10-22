package com.salesianos.dam.talleresculturales.service;

import com.salesianos.dam.talleresculturales.dto.TallerEditDTO;
import com.salesianos.dam.talleresculturales.exception.ParticipanteYaInscritoException;
import com.salesianos.dam.talleresculturales.exception.TallerNoEncontradoException;
import com.salesianos.dam.talleresculturales.exception.TallerYaExisteException;
import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.model.Participante;
import com.salesianos.dam.talleresculturales.model.Taller;
import com.salesianos.dam.talleresculturales.repository.TallerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TallerService {

    private final TallerRepository tallerRepository;
    private final OrganizadorService organizadorService;
    private final ParticipanteService participanteService;

    public List<Taller> getAllTalleres() {
        return tallerRepository.findAll();
    }

    public Taller getTallerById(Long id) {
        return tallerRepository.findById(id)
                .orElseThrow(() -> new TallerNoEncontradoException(id));
    }

    public Taller addTaller(TallerEditDTO dto) {

        Organizador organizador = organizadorService.getOrganizadorById(dto.organizadorId());
        String titulo = dto.titulo().trim();

        if (tallerRepository.existsByTituloIgnoreCase(titulo)) {
            throw new TallerYaExisteException(titulo);
        }
        return tallerRepository.save(dto.to(organizador));
    }

    public Taller updateTaller(Long id, TallerEditDTO dto) {

        Taller existente = getTallerById(id);
        Organizador organizador = organizadorService.getOrganizadorById(dto.organizadorId());
        String titulo = dto.titulo().trim();

        if (tallerRepository.existsByTituloIgnoreCaseAndIdNot(titulo, id)) {
            throw new TallerYaExisteException(titulo);
        }

        existente.setTitulo(titulo);
        existente.setFechaHoraInicio(dto.fechaHoraInicio());
        existente.setFechaHoraFin(dto.fechaHoraFin());
        existente.setOrganizador(organizador);

        return tallerRepository.save(existente);
    }

    public void deleteTaller(Long id) {

        Taller existente = getTallerById(id);

        tallerRepository.delete(existente);
    }

    public Taller inscribirParticipante(Long tallerId, Long participanteId) {

        Taller taller = getTallerById(tallerId);
        Participante participante = participanteService.getParticipanteById(participanteId);

        if (taller.getParticipantes().contains(participante)) {
            throw new ParticipanteYaInscritoException(participante.getNombreCompleto(), taller.getTitulo());
        }

        taller.getParticipantes().add(participante);

        return tallerRepository.save(taller);

    }

    public void desinscribirParticipante(Long tallerId, Long participanteId) {
        Taller taller = getTallerById(tallerId);
        Participante participante = participanteService.getParticipanteById(participanteId);

        taller.getParticipantes().remove(participante);

        tallerRepository.save(taller);
    }

}


