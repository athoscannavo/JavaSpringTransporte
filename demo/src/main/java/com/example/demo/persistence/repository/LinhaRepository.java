package com.example.demo.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistence.entity.LinhaEntity;

public interface LinhaRepository extends JpaRepository<LinhaEntity, UUID> {
}
