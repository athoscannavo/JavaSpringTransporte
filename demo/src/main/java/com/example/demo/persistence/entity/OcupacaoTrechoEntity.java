package com.example.demo.persistence.entity;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ocupacoes_trecho", uniqueConstraints = {
        @UniqueConstraint(name = "uk_ocupacao_onibus_trecho",
                columnNames = { "onibus_id", "parada_inicial_id", "parada_final_id" })
})
public class OcupacaoTrechoEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "onibus_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ocupacao_onibus"))
    private OnibusEntity onibus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parada_inicial_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ocupacao_parada_inicial"))
    private ParadaEntity paradaInicial;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parada_final_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ocupacao_parada_final"))
    private ParadaEntity paradaFinal;

    @Column(nullable = false)
    private int passageiros;

    protected OcupacaoTrechoEntity() {
    }

    public OcupacaoTrechoEntity(
            UUID id,
            OnibusEntity onibus,
            ParadaEntity paradaInicial,
            ParadaEntity paradaFinal,
            int passageiros) {
        this.id = Objects.requireNonNull(id, "O identificador da ocupação é obrigatório.");
        this.onibus = Objects.requireNonNull(onibus, "O ônibus da ocupação é obrigatório.");
        this.paradaInicial = Objects.requireNonNull(paradaInicial, "A parada inicial é obrigatória.");
        this.paradaFinal = Objects.requireNonNull(paradaFinal, "A parada final é obrigatória.");
        this.passageiros = passageiros;
    }

    public UUID getId() {
        return id;
    }

    public OnibusEntity getOnibus() {
        return onibus;
    }

    public ParadaEntity getParadaInicial() {
        return paradaInicial;
    }

    public ParadaEntity getParadaFinal() {
        return paradaFinal;
    }

    public int getPassageiros() {
        return passageiros;
    }
}
