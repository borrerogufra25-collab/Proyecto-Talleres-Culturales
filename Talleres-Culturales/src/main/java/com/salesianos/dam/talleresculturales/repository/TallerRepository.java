package com.salesianos.dam.talleresculturales.repository;

import com.salesianos.dam.talleresculturales.model.Taller;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TallerRepository extends JpaRepository<Taller, Long> {

    boolean existsByTituloIgnoreCase(String titulo);

    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Long id);


}