package com.example.demo.persistence.entity;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "linhas")
public class LinhaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    protected LinhaEntity() {
    }

    public LinhaEntity(UUID id, String nome) {
        this.id = Objects.requireNonNull(id, "O identificador da linha é obrigatório.");
        this.nome = Objects.requireNonNull(nome, "O nome da linha é obrigatório.");
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
