package com.salesianos.dam.talleresculturales.service;

import com.salesianos.dam.talleresculturales.dto.OrganizadorEditDTO;
import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.repository.OrganizadorRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizadorService {

  private OrganizadorRepository organizadorRepository;

  public List<Organizador> getAllOrganizadores() {

    List<Organizador> resultado = organizadorRepository.findAll();

    if (resultado.isEmpty()) {
      return null;
    }
    return resultado;
  }

  public Optional<Organizador> getOrganizadorById(Long id) {

    return organizadorRepository.findById(id);
  }

  public Organizador addOrganizador(Organizador organizador) {

    return organizadorRepository.save(organizador);
  }

  /*public Organizador updateOrganizador(Long id, Organizador organizador){
    return
  }*/

  public void deleteOrganizador(Long id) {
    organizadorRepository.deleteById(id);
  }

}