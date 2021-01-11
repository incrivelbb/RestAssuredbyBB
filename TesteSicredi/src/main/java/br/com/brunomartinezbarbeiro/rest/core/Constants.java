package br.com.brunomartinezbarbeiro.rest.core;

import io.restassured.http.ContentType;

public interface Constants {

    // Inserir URL do ambiente a ser testado
    String BASE_URL = "http://localhost:8080/api/v1/";
    // Endpoint para cpf com restriçao
    // RESTRICTIONS_ENDPOINT = "/api/v1/restricoes/";
    // Endpoint para transações com simulação de crédito
    // SIMULATIONS_ENDPOINT = "/api/v1/simulacoes/";

    ContentType CONTENT_TYPE = ContentType.JSON;

}
