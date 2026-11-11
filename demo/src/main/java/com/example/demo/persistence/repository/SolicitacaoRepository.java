package com.example.demo.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistence.entity.SolicitacaoEntity;

public interface SolicitacaoRepository extends JpaRepository<SolicitacaoEntity, UUID> {
}
