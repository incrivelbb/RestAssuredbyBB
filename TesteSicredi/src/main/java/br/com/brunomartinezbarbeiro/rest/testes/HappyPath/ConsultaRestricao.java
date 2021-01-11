package br.com.brunomartinezbarbeiro.rest.testes.HappyPath;

import br.com.brunomartinezbarbeiro.rest.core.BaseTest;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class ConsultaRestricao extends BaseTest {

    // CPFs com restrição;
    public static String CPF_1 = "97093236014";
    public static String CPF_2  = "60094146012";
    public static String CPF_3 = "84809766080";

    // CPFs sem restrição;
    public static String CPF_LIMPO_1 = "97093886014";
    public static String CPF_LIMPO_2  = "60044149012";
    public static String CPF_LIMPO_3 = "35819766096";


    @Test
    public void consultaCPFsComRestricao() {

    // Consulta CPFs da massa de dados com restrição =  verdadeiro

        given()
        .when()
          .log().all()
          .get("restricoes/"+CPF_1+"")
        .then()
           .log().all()
           .statusCode(200)
           .body(containsString("O CPF "+CPF_1+" possui restrição"))
        ;

        given()
        .when()
           .log().all()
           .get("restricoes/"+CPF_2+"")
        .then()
           .log().all()
           .statusCode(200)
           .body(containsString("O CPF "+CPF_2+" possui restrição"))
        ;

        given()
        .when()
          .log().all()
          .get("restricoes/"+CPF_3+"")
        .then()
           .log().all()
           .statusCode(200)
           .body(containsString("O CPF "+CPF_3+" possui restrição"))
        ;
    }

    @Test
    public void consultaCPFsSemRestricao() {

        // Consulta CPFs da massa de dados com restrição =  falso

        given()
        .when()
           .log().all()
           .get("restricoes/"+CPF_LIMPO_1+"")
        .then()
           .log().all()
           .statusCode(204)
        ;

        given()
        .when()
          .log().all()
          .get("restricoes/"+CPF_LIMPO_2+"")
        .then()
          .log().all()
          .statusCode(204)
        ;

        given()
        .when()
          .log().all()
          .get("restricoes/"+CPF_LIMPO_3+"")
        .then()
          .log().all()
          .statusCode(204)
        ;
    }
}
