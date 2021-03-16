package br.com.brunomartinezbarbeiro.rest.core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;


// Teste base para rodar os CTs

public class BaseTest implements Constants {

    @BeforeClass
    public static void setup () {

    // Metodo que faz o setup da request

        RestAssured.baseURI = BASE_URL;

        // Setup da request
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setContentType(CONTENT_TYPE);
        RestAssured.requestSpecification = requestBuilder.build();

        // Setup da response (pode usar para testar timeout e etc)
        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        RestAssured.responseSpecification = responseBuilder.build();

        // Habilita log de erro se o CT falhar
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();


    }
}
