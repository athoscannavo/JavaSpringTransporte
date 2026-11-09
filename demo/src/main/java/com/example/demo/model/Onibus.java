package com.example.demo.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record Onibus(
        UUID id,
        UUID linhaId,
        int capacidade,
        LocalTime horarioSaida,
        EstadoOnibus estado,
        UUID paradaAtualId,
        List<OcupacaoTrecho> ocupacaoPorTrecho) {

    public Onibus {
        Objects.requireNonNull(id, "O identificador do ônibus é obrigatório.");
        Objects.requireNonNull(linhaId, "A linha do ônibus é obrigatória.");
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade do ônibus deve ser positiva.");
        }
        Objects.requireNonNull(horarioSaida, "O horário de saída é obrigatório.");
        Objects.requireNonNull(estado, "O estado do ônibus é obrigatório.");
        Objects.requireNonNull(paradaAtualId, "A posição atual do ônibus é obrigatória.");
        ocupacaoPorTrecho = List.copyOf(
                Objects.requireNonNull(ocupacaoPorTrecho, "A ocupação por trecho é obrigatória."));
        for (int i = 0; i < ocupacaoPorTrecho.size(); i++) {
            OcupacaoTrecho trecho = ocupacaoPorTrecho.get(i);
            if (trecho.passageiros() > capacidade) {
                throw new IllegalArgumentException("A ocupação do trecho não pode exceder a capacidade do ônibus.");
            }
            if (i > 0 && !ocupacaoPorTrecho.get(i - 1).paradaFinalId().equals(trecho.paradaInicialId())) {
                throw new IllegalArgumentException("Os trechos de ocupação devem ser consecutivos.");
            }
        }
    }

    public static Onibus criar(
            Linha linha,
            int capacidade,
            LocalTime horarioSaida,
            EstadoOnibus estado,
            Parada paradaAtual) {
        Objects.requireNonNull(linha, "A linha do ônibus é obrigatória.");
        validarParadaDaLinha(linha, paradaAtual);

        List<Parada> paradas = linha.paradas();
        List<OcupacaoTrecho> ocupacao = new ArrayList<>();
        for (int i = 0; i < paradas.size() - 1; i++) {
            ocupacao.add(new OcupacaoTrecho(paradas.get(i).id(), paradas.get(i + 1).id(), 0));
        }
        return new Onibus(
                UUID.randomUUID(),
                linha.id(),
                capacidade,
                horarioSaida,
                estado,
                paradaAtual.id(),
                ocupacao);
    }

    public Onibus comParadaAtual(Linha linha, Parada paradaAtual) {
        validarLinha(linha);
        validarParadaDaLinha(linha, paradaAtual);
        validarOcupacaoDaLinha(linha, ocupacaoPorTrecho);
        return new Onibus(id, linhaId, capacidade, horarioSaida, estado, paradaAtual.id(), ocupacaoPorTrecho);
    }

    public Onibus comOcupacaoPorTrecho(Linha linha, List<OcupacaoTrecho> novaOcupacao) {
        validarLinha(linha);
        if (linha.paradas().stream().noneMatch(parada -> parada.id().equals(paradaAtualId))) {
            throw new IllegalArgumentException("A posição atual deve ser uma parada da linha do ônibus.");
        }
        validarOcupacaoDaLinha(linha, novaOcupacao);
        return new Onibus(id, linhaId, capacidade, horarioSaida, estado, paradaAtualId, novaOcupacao);
    }

    private void validarLinha(Linha linha) {
        Objects.requireNonNull(linha, "A linha do ônibus é obrigatória.");
        if (!linhaId.equals(linha.id())) {
            throw new IllegalArgumentException("A linha informada não corresponde à linha do ônibus.");
        }
    }

    private static void validarOcupacaoDaLinha(Linha linha, List<OcupacaoTrecho> ocupacao) {
        Objects.requireNonNull(ocupacao, "A ocupação por trecho é obrigatória.");
        List<Parada> paradas = linha.paradas();
        if (ocupacao.size() != paradas.size() - 1) {
            throw new IllegalArgumentException("Deve haver uma ocupação para cada trecho consecutivo da linha.");
        }
        for (int i = 0; i < ocupacao.size(); i++) {
            OcupacaoTrecho trecho = Objects.requireNonNull(ocupacao.get(i), "O trecho de ocupação é obrigatório.");
            if (!trecho.paradaInicialId().equals(paradas.get(i).id())
                    || !trecho.paradaFinalId().equals(paradas.get(i + 1).id())) {
                throw new IllegalArgumentException("A ocupação deve corresponder aos trechos consecutivos da linha.");
            }
        }
    }

    private static void validarParadaDaLinha(Linha linha, Parada parada) {
        Objects.requireNonNull(parada, "A posição atual do ônibus é obrigatória.");
        if (!linha.id().equals(parada.linhaId())
                || linha.paradas().stream().noneMatch(paradaDaLinha -> paradaDaLinha.id().equals(parada.id()))) {
            throw new IllegalArgumentException("A posição atual deve ser uma parada da linha do ônibus.");
        }
    }
}
