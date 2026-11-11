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
@Table(name = "paradas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_parada_linha_ordem", columnNames = { "linha_id", "ordem" })
})
public class ParadaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "linha_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_parada_linha"))
    private LinhaEntity linha;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private int ordem;

    protected ParadaEntity() {
    }

    public ParadaEntity(UUID id, LinhaEntity linha, int numero, int ordem) {
        this.id = Objects.requireNonNull(id, "O identificador da parada é obrigatório.");
        this.linha = Objects.requireNonNull(linha, "A linha da parada é obrigatória.");
        this.numero = numero;
        this.ordem = ordem;
    }

    public UUID getId() {
        return id;
    }

    public LinhaEntity getLinha() {
        return linha;
    }

    public int getNumero() {
        return numero;
    }

    public int getOrdem() {
        return ordem;
    }
}
