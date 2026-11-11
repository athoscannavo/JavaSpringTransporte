package com.example.demo.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistence.entity.ParadaEntity;

public interface ParadaRepository extends JpaRepository<ParadaEntity, UUID> {

    List<ParadaEntity> findByLinha_IdOrderByOrdemAsc(UUID linhaId);
}
