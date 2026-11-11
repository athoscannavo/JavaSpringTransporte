package com.example.demo.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.demo.persistence.entity.LinhaEntity;
import com.example.demo.persistence.repository.LinhaRepository;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:linhas-api-test")
class LinhaControllerTests {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    @Autowired
    private LinhaRepository linhaRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void limparLinhas() {
        linhaRepository.deleteAll();
    }

    @Test
    void retornaListaVaziaQuandoNaoHaLinhasCadastradas() throws Exception {
        HttpResponse<String> resposta = httpGetLinhas();

        assertEquals(200, resposta.statusCode());
        assertEquals("[]", resposta.body());
    }

    @Test
    void retornaIdentificadorENomeDasLinhasPersistidas() throws Exception {
        UUID id = UUID.randomUUID();
        linhaRepository.save(new LinhaEntity(id, "Linha Centro"));

        HttpResponse<String> resposta = httpGetLinhas();

        assertEquals(200, resposta.statusCode());
        assertTrue(resposta.body().contains("\"id\":\"" + id + "\""));
        assertTrue(resposta.body().contains("\"nome\":\"Linha Centro\""));
    }

    private HttpResponse<String> httpGetLinhas() throws Exception {
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/linhas"))
                .GET()
                .build();
        return HTTP_CLIENT.send(requisicao, HttpResponse.BodyHandlers.ofString());
    }
}
