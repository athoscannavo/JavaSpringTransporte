package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class ModelosTransporteTests {

    @Test
    void factoriesGenerateDistinctStableIdentifiers() {
        Linha linha = Linha.criar("Linha Centro");
        Parada parada = Parada.criar(linha.id(), 1, 1);
        linha = linha.comParadas(List.of(parada));
        Onibus onibus = Onibus.criar(linha, 40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO, parada);

        assertNotEquals(Linha.criar("Linha Centro").id(), linha.id());
        assertNotEquals(Onibus.criar(linha, 40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO, parada).id(),
                onibus.id());
        assertNotEquals(Parada.criar(linha.id(), 1, 1).id(), parada.id());
        assertEquals(linha.id(), new Linha(linha.id(), linha.nome()).id());
        assertEquals(onibus.id(), new Onibus(onibus.id(), onibus.linhaId(), onibus.capacidade(),
                onibus.horarioSaida(), onibus.estado(), onibus.paradaAtualId(), onibus.ocupacaoPorTrecho()).id());
        assertEquals(parada.id(), new Parada(parada.id(), parada.linhaId(),
                parada.numero(), parada.ordem()).id());
    }

    @Test
    void modelsRejectInvalidRequiredValues() {
        assertThrows(NullPointerException.class, () -> new Linha(null, "Linha Centro"));
        assertThrows(IllegalArgumentException.class, () -> Linha.criar(" "));
        Linha linha = Linha.criar("Linha Centro");
        Parada parada = Parada.criar(linha.id(), 1, 1);
        linha = linha.comParadas(List.of(parada));
        Linha linhaComParada = linha;
        assertThrows(NullPointerException.class, () -> Onibus.criar(
                linhaComParada, 40, null, EstadoOnibus.ESTACIONADO, parada));
        assertThrows(IllegalArgumentException.class, () -> Onibus.criar(
                linhaComParada, 0, LocalTime.NOON, EstadoOnibus.ESTACIONADO, parada));
        assertThrows(NullPointerException.class, () -> Onibus.criar(
                linhaComParada, 40, LocalTime.NOON, null, parada));
        assertThrows(NullPointerException.class, () -> Onibus.criar(
                linhaComParada, 40, LocalTime.NOON, EstadoOnibus.ESTACIONADO, null));
        assertThrows(IllegalArgumentException.class, () -> Parada.criar(UUID.randomUUID(), 0, 1));
        assertThrows(IllegalArgumentException.class, () -> Parada.criar(UUID.randomUUID(), 1, 0));
    }

    @Test
    void linhaOrdenaParadasEAsMantemImutaveis() {
        Linha linha = Linha.criar("Linha Centro");
        Parada segunda = Parada.criar(linha.id(), 20, 2);
        Parada primeira = Parada.criar(linha.id(), 10, 1);

        Linha linhaComParadas = linha.comParadas(List.of(segunda, primeira));

        assertEquals(List.of(primeira, segunda), linhaComParadas.paradas());
        assertThrows(UnsupportedOperationException.class, () -> linhaComParadas.paradas().clear());
    }

    @Test
    void linhaRejeitaParadasDeOutraLinhaEOrdensDuplicadas() {
        Linha linha = Linha.criar("Linha Centro");
        Parada parada = Parada.criar(linha.id(), 1, 1);

        assertThrows(IllegalArgumentException.class, () -> linha.comParadas(
                List.of(Parada.criar(UUID.randomUUID(), 2, 2))));
        assertThrows(IllegalArgumentException.class, () -> linha.comParadas(
                List.of(parada, Parada.criar(linha.id(), 2, 1))));
    }

    @Test
    void onibusRequerLinhaAssociada() {
        assertThrows(NullPointerException.class, () -> Onibus.criar(
                null, 40, LocalTime.NOON, EstadoOnibus.ESTACIONADO, null));
    }

    @Test
    void onibusInicializaUmaOcupacaoParaCadaTrechoConsecutivo() {
        Linha linha = Linha.criar("Linha Centro");
        Parada primeira = Parada.criar(linha.id(), 10, 1);
        Parada segunda = Parada.criar(linha.id(), 20, 2);
        Parada terceira = Parada.criar(linha.id(), 30, 3);
        linha = linha.comParadas(List.of(terceira, primeira, segunda));

        Onibus onibus = Onibus.criar(linha, 40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO, segunda);

        assertEquals(segunda.id(), onibus.paradaAtualId());
        assertEquals(List.of(
                new OcupacaoTrecho(primeira.id(), segunda.id(), 0),
                new OcupacaoTrecho(segunda.id(), terceira.id(), 0)), onibus.ocupacaoPorTrecho());
        assertThrows(UnsupportedOperationException.class, () -> onibus.ocupacaoPorTrecho().clear());
    }

    @Test
    void onibusRejeitaPosicaoDeOutraLinha() {
        Linha linha = Linha.criar("Linha Centro");
        Parada parada = Parada.criar(linha.id(), 1, 1);
        linha = linha.comParadas(List.of(parada));
        Linha linhaComParada = linha;

        assertThrows(IllegalArgumentException.class, () -> Onibus.criar(
                linhaComParada, 40, LocalTime.NOON, EstadoOnibus.ESTACIONADO,
                Parada.criar(UUID.randomUUID(), 2, 1)));
    }

    @Test
    void ocupacaoPorTrechoRespeitaLimitesECorrespondeAoPercurso() {
        Linha linha = Linha.criar("Linha Centro");
        Parada primeira = Parada.criar(linha.id(), 1, 1);
        Parada segunda = Parada.criar(linha.id(), 2, 2);
        Parada terceira = Parada.criar(linha.id(), 3, 3);
        linha = linha.comParadas(List.of(primeira, segunda, terceira));
        Linha linhaComParadas = linha;
        Onibus onibus = Onibus.criar(linha, 40, LocalTime.NOON, EstadoOnibus.ESTACIONADO, primeira);

        Onibus atualizado = onibus.comOcupacaoPorTrecho(linha, List.of(
                new OcupacaoTrecho(primeira.id(), segunda.id(), 20),
                new OcupacaoTrecho(segunda.id(), terceira.id(), 40)));

        assertEquals(20, atualizado.ocupacaoPorTrecho().get(0).passageiros());
        assertThrows(IllegalArgumentException.class, () -> new OcupacaoTrecho(
                primeira.id(), segunda.id(), -1));
        assertThrows(IllegalArgumentException.class, () -> onibus.comOcupacaoPorTrecho(linhaComParadas, List.of(
                new OcupacaoTrecho(primeira.id(), segunda.id(), 1))));
        assertThrows(IllegalArgumentException.class, () -> onibus.comOcupacaoPorTrecho(linhaComParadas, List.of(
                new OcupacaoTrecho(primeira.id(), terceira.id(), 1),
                new OcupacaoTrecho(segunda.id(), terceira.id(), 1))));
        assertThrows(IllegalArgumentException.class, () -> new Onibus(
                onibus.id(), onibus.linhaId(), onibus.capacidade(), onibus.horarioSaida(), onibus.estado(),
                onibus.paradaAtualId(), List.of(new OcupacaoTrecho(primeira.id(), segunda.id(), 41))));
    }
}
