package com.example.demo.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistence.entity.OcupacaoTrechoEntity;

public interface OcupacaoTrechoRepository extends JpaRepository<OcupacaoTrechoEntity, UUID> {

    List<OcupacaoTrechoEntity> findByOnibus_Id(UUID onibusId);
}
