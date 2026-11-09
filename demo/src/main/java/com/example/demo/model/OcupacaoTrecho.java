package com.example.demo.model;

import java.util.Objects;
import java.util.UUID;

public record OcupacaoTrecho(UUID paradaInicialId, UUID paradaFinalId, int passageiros) {

    public OcupacaoTrecho {
        Objects.requireNonNull(paradaInicialId, "A parada inicial do trecho é obrigatória.");
        Objects.requireNonNull(paradaFinalId, "A parada final do trecho é obrigatória.");
        if (paradaInicialId.equals(paradaFinalId)) {
            throw new IllegalArgumentException("As paradas inicial e final do trecho devem ser diferentes.");
        }
        if (passageiros < 0) {
            throw new IllegalArgumentException("A ocupação do trecho não pode ser negativa.");
        }
    }
}
