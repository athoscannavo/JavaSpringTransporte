package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistence.entity.LinhaEntity;
import com.example.demo.persistence.repository.LinhaRepository;

@RestController
@RequestMapping("/api/linhas")
public class LinhaController {

    private final LinhaRepository linhaRepository;

    public LinhaController(LinhaRepository linhaRepository) {
        this.linhaRepository = linhaRepository;
    }

    @GetMapping
    public List<LinhaResponse> listarLinhas() {
        return linhaRepository.findAll().stream()
                .map(LinhaController::paraResposta)
                .toList();
    }

    private static LinhaResponse paraResposta(LinhaEntity linha) {
        return new LinhaResponse(linha.getId(), linha.getNome());
    }

    public record LinhaResponse(UUID id, String nome) {
    }
}
