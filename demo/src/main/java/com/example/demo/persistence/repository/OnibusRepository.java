package com.example.demo.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistence.entity.OnibusEntity;

public interface OnibusRepository extends JpaRepository<OnibusEntity, UUID> {

    List<OnibusEntity> findByLinha_Id(UUID linhaId);
}
