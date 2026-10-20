package com.salesianos.dam.talleresculturales.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Taller {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false)
    private Organizador organizador;

    @ManyToMany
    @JoinTable(
            name = "inscripcion",
            joinColumns = @JoinColumn(name = "taller_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    @Builder.Default
    private List<Participante> participantes = new ArrayList<>();

}
