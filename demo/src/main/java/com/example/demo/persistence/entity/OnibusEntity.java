package com.example.demo.persistence.entity;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import com.example.demo.model.EstadoOnibus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "onibus")
public class OnibusEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "linha_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_onibus_linha"))
    private LinhaEntity linha;

    @Column(nullable = false)
    private int capacidade;

    @Column(name = "horario_saida", nullable = false)
    private LocalTime horarioSaida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOnibus estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parada_atual_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_onibus_parada_atual"))
    private ParadaEntity paradaAtual;

    protected OnibusEntity() {
    }

    public OnibusEntity(
            UUID id,
            LinhaEntity linha,
            int capacidade,
            LocalTime horarioSaida,
            EstadoOnibus estado,
            ParadaEntity paradaAtual) {
        this.id = Objects.requireNonNull(id, "O identificador do ônibus é obrigatório.");
        this.linha = Objects.requireNonNull(linha, "A linha do ônibus é obrigatória.");
        this.capacidade = capacidade;
        this.horarioSaida = Objects.requireNonNull(horarioSaida, "O horário de saída é obrigatório.");
        this.estado = Objects.requireNonNull(estado, "O estado do ônibus é obrigatório.");
        this.paradaAtual = Objects.requireNonNull(paradaAtual, "A posição atual do ônibus é obrigatória.");
    }

    public UUID getId() {
        return id;
    }

    public LinhaEntity getLinha() {
        return linha;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public LocalTime getHorarioSaida() {
        return horarioSaida;
    }

    public EstadoOnibus getEstado() {
        return estado;
    }

    public ParadaEntity getParadaAtual() {
        return paradaAtual;
    }
}
