package com.salesianos.dam.talleresculturales.service;

import com.salesianos.dam.talleresculturales.exception.OrganizadorNoEncontradoException;
import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.repository.OrganizadorRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizadorService {

    private final OrganizadorRepository organizadorRepository;

    public List<Organizador> getAllOrganizadores() {

        return organizadorRepository.findAll();
    }

    public Organizador getOrganizadorById(Long id) {

        return organizadorRepository.findById(id)
                .orElseThrow(() -> new OrganizadorNoEncontradoException(id));
    }

    public Organizador addOrganizador(Organizador organizador) {

        return organizadorRepository.save(organizador);
    }

    public Organizador updateOrganizador(Long id, Organizador organizador) {

        Organizador existente = getOrganizadorById(id);

        existente.setNombre(organizador.getNombre());
        existente.setEmail(organizador.getEmail());

        return organizadorRepository.save(existente);
    }

    public void deleteOrganizador(Long id) {

        Organizador existente = getOrganizadorById(id);

        organizadorRepository.delete(existente);
    }

}