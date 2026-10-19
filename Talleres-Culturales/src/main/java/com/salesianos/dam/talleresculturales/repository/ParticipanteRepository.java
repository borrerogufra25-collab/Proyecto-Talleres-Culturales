package com.salesianos.dam.talleresculturales.repository;

import com.salesianos.dam.talleresculturales.model.Participante;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
}