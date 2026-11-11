package com.example.demo.persistence.entity;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitacoes")
public class SolicitacaoEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "onibus_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_solicitacao_onibus"))
    private OnibusEntity onibus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parada_embarque_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_solicitacao_parada_embarque"))
    private ParadaEntity paradaEmbarque;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parada_desembarque_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_solicitacao_parada_desembarque"))
    private ParadaEntity paradaDesembarque;

    protected SolicitacaoEntity() {
    }

    public SolicitacaoEntity(
            UUID id,
            OnibusEntity onibus,
            ParadaEntity paradaEmbarque,
            ParadaEntity paradaDesembarque) {
        this.id = Objects.requireNonNull(id, "O identificador da solicitação é obrigatório.");
        this.onibus = Objects.requireNonNull(onibus, "O ônibus da solicitação é obrigatório.");
        this.paradaEmbarque = Objects.requireNonNull(paradaEmbarque, "A parada de embarque é obrigatória.");
        this.paradaDesembarque = Objects.requireNonNull(paradaDesembarque,
                "A parada de desembarque é obrigatória.");
    }

    public UUID getId() {
        return id;
    }

    public OnibusEntity getOnibus() {
        return onibus;
    }

    public ParadaEntity getParadaEmbarque() {
        return paradaEmbarque;
    }

    public ParadaEntity getParadaDesembarque() {
        return paradaDesembarque;
    }
}
