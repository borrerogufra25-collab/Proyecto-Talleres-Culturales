package com.salesianos.dam.talleresculturales.service;


import com.salesianos.dam.talleresculturales.exception.ParticipanteNoEncontrado;
import com.salesianos.dam.talleresculturales.model.Participante;
import com.salesianos.dam.talleresculturales.repository.ParticipanteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

  private final ParticipanteRepository participanteRepository;

  public List<Participante> getAllParticipantes() {

    return participanteRepository.findAll();
  }

  public Participante getParticipanteById(Long id) {

    return participanteRepository.findById(id)
        .orElseThrow(() -> new ParticipanteNoEncontrado(id));
  }

  public Participante addParticipante(Participante participante) {

    return participanteRepository.save(participante);
  }

  public Participante updateParticipante(Long id, Participante participante) {

    Participante existente = getParticipanteById(id);

    existente.setNombreCompleto(participante.getNombreCompleto());

    return participanteRepository.save(existente);
  }

  public void deleteParticipante(Long id) {

    Participante existente = getParticipanteById(id);

    participanteRepository.delete(existente);
  }


}
