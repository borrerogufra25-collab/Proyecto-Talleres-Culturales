package com.salesianos.dam.talleresculturales.config;

import com.salesianos.dam.talleresculturales.model.Organizador;
import com.salesianos.dam.talleresculturales.model.Participante;
import com.salesianos.dam.talleresculturales.model.Taller;
import com.salesianos.dam.talleresculturales.repository.OrganizadorRepository;
import com.salesianos.dam.talleresculturales.repository.ParticipanteRepository;
import com.salesianos.dam.talleresculturales.repository.TallerRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeed {

    private final OrganizadorRepository organizadorRepository;
    private final TallerRepository tallerRepository;
    private final ParticipanteRepository participanteRepository;

    @Bean
    CommandLineRunner cargarDatos() {
        return args -> {

            Organizador ana = organizadorRepository.save(Organizador.builder()
                    .nombre("Ana López")
                    .email("ana@cultura.es")
                    .build());

            Organizador luis = organizadorRepository.save(Organizador.builder()
                    .nombre("Luis Martín")
                    .email("luis@cultura.es")
                    .build());

            Participante p1 = participanteRepository.save(Participante.builder()
                    .nombreCompleto("María García Pérez").build());
            Participante p2 = participanteRepository.save(Participante.builder()
                    .nombreCompleto("Carlos Ruiz Soto").build());
            Participante p3 = participanteRepository.save(Participante.builder()
                    .nombreCompleto("Lucía Fernández Gil").build());

            LocalDateTime base = LocalDateTime.now().plusDays(7).withMinute(0).withSecond(0).withNano(0);

            // Taller con participantes
            tallerRepository.save(Taller.builder()
                    .titulo("Cerámica")
                    .fechaHoraInicio(base.withHour(10))
                    .fechaHoraFin(base.withHour(12))
                    .organizador(ana)
                    .participantes(new ArrayList<>(List.of(p1, p2)))
                    .build());

            // Taller con un participante
            tallerRepository.save(Taller.builder()
                    .titulo("Fotografía")
                    .fechaHoraInicio(base.plusDays(1).withHour(17))
                    .fechaHoraFin(base.plusDays(1).withHour(19))
                    .organizador(luis)
                    .participantes(new ArrayList<>(List.of(p3)))
                    .build());

            // Taller SIN participantes (para probar la lista vacía)
            tallerRepository.save(Taller.builder()
                    .titulo("Teatro")
                    .fechaHoraInicio(base.plusDays(2).withHour(18))
                    .fechaHoraFin(base.plusDays(2).withHour(20))
                    .organizador(ana)
                    .build());
        };
    }
}