package com.example.demo.model;

import java.util.Objects;
import java.util.UUID;

public record Parada(UUID id, UUID linhaId, int numero, int ordem) {

    public Parada {
        Objects.requireNonNull(id, "O identificador da parada é obrigatório.");
        Objects.requireNonNull(linhaId, "A linha da parada é obrigatória.");
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da parada deve ser positivo.");
        }
        if (ordem <= 0) {
            throw new IllegalArgumentException("A ordem da parada deve ser positiva.");
        }
    }

    public static Parada criar(UUID linhaId, int numero, int ordem) {
        return new Parada(UUID.randomUUID(), linhaId, numero, ordem);
    }
}
