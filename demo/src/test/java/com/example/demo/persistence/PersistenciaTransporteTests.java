package com.example.demo.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.EstadoOnibus;
import com.example.demo.persistence.entity.LinhaEntity;
import com.example.demo.persistence.entity.OcupacaoTrechoEntity;
import com.example.demo.persistence.entity.OnibusEntity;
import com.example.demo.persistence.entity.ParadaEntity;
import com.example.demo.persistence.entity.SolicitacaoEntity;
import com.example.demo.persistence.repository.LinhaRepository;
import com.example.demo.persistence.repository.OcupacaoTrechoRepository;
import com.example.demo.persistence.repository.OnibusRepository;
import com.example.demo.persistence.repository.ParadaRepository;
import com.example.demo.persistence.repository.SolicitacaoRepository;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:transporte-test")
@Transactional
class PersistenciaTransporteTests {

    @Autowired
    private LinhaRepository linhaRepository;

    @Autowired
    private ParadaRepository paradaRepository;

    @Autowired
    private OnibusRepository onibusRepository;

    @Autowired
    private OcupacaoTrechoRepository ocupacaoTrechoRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Test
    void persisteEntidadesDeTransporteERelacionamentos() {
        LinhaEntity linha = linhaRepository.save(new LinhaEntity(UUID.randomUUID(), "Linha Centro"));
        ParadaEntity embarque = paradaRepository.save(
                new ParadaEntity(UUID.randomUUID(), linha, 10, 1));
        ParadaEntity desembarque = paradaRepository.save(
                new ParadaEntity(UUID.randomUUID(), linha, 20, 2));
        OnibusEntity onibus = onibusRepository.save(new OnibusEntity(
                UUID.randomUUID(), linha, 40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO, embarque));

        OcupacaoTrechoEntity ocupacao = ocupacaoTrechoRepository.save(new OcupacaoTrechoEntity(
                UUID.randomUUID(), onibus, embarque, desembarque, 3));
        SolicitacaoEntity solicitacao = solicitacaoRepository.save(new SolicitacaoEntity(
                UUID.randomUUID(), onibus, embarque, desembarque));

        assertThat(paradaRepository.findByLinha_IdOrderByOrdemAsc(linha.getId()))
                .extracting(ParadaEntity::getId)
                .containsExactly(embarque.getId(), desembarque.getId());
        assertThat(onibusRepository.findByLinha_Id(linha.getId()))
                .extracting(OnibusEntity::getId)
                .containsExactly(onibus.getId());
        assertThat(ocupacaoTrechoRepository.findByOnibus_Id(onibus.getId()))
                .extracting(OcupacaoTrechoEntity::getPassageiros)
                .containsExactly(3);
        assertThat(solicitacaoRepository.findById(solicitacao.getId()))
                .get()
                .extracting(SolicitacaoEntity::getOnibus)
                .extracting(OnibusEntity::getId)
                .isEqualTo(onibus.getId());
        assertThat(linhaRepository.findById(linha.getId())).isPresent();
        assertThat(ocupacao.getParadaInicial().getId()).isEqualTo(embarque.getId());
    }
}
