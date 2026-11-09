package com.example.demo.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record Linha(UUID id, String nome, List<Parada> paradas) {

    public Linha {
        Objects.requireNonNull(id, "O identificador da linha é obrigatório.");
        Objects.requireNonNull(nome, "O nome da linha é obrigatório.");
        if (nome.isBlank()) {
            throw new IllegalArgumentException("O nome da linha não pode estar vazio.");
        }
        Objects.requireNonNull(paradas, "A lista de paradas é obrigatória.");

        Set<Integer> ordens = new HashSet<>();
        for (Parada parada : paradas) {
            Objects.requireNonNull(parada, "A parada da linha é obrigatória.");
            if (!id.equals(parada.linhaId())) {
                throw new IllegalArgumentException("Todas as paradas devem pertencer à linha.");
            }
            if (!ordens.add(parada.ordem())) {
                throw new IllegalArgumentException("A ordem das paradas deve ser única dentro da linha.");
            }
        }
        paradas = paradas.stream()
                .sorted(Comparator.comparingInt(Parada::ordem))
                .toList();
    }

    public Linha(UUID id, String nome) {
        this(id, nome, List.of());
    }

    public static Linha criar(String nome) {
        return new Linha(UUID.randomUUID(), nome, List.of());
    }

    public Linha comParadas(List<Parada> paradas) {
        return new Linha(id, nome, paradas);
    }
}
