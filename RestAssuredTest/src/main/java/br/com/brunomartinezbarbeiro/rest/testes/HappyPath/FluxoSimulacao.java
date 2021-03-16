package br.com.brunomartinezbarbeiro.rest.testes.HappyPath;

import br.com.brunomartinezbarbeiro.rest.testes.Conta;
import br.com.brunomartinezbarbeiro.rest.core.BaseTest;

import io.restassured.RestAssured;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxoSimulacao extends BaseTest {

    //dados para primeiro fluxo
    public static String CPF = "39351800040";
    public static String EMAIL = "bruno@test.com";
    private static Integer ID = 7777;
    private static String NOME = "Bruno Test";
    private static Integer PARCELAS = 24;
    private static Boolean SEGURO = true;
    private static Number VALOR = 1000.0;

    //dados após alteração
    public static String CPF_ALT = "45798863312";
    public static String EMAIL_ALT = "test@bruno.com";
    private static Integer ID_ALT = 19;
    private static String NOME_ALT = "Edição realizada";
    private static Integer PARCELAS_ALT = 15;
    private static Boolean SEGURO_ALT = false;
    private static Number VALOR_ALT = 40000.0;

    @Test
    public void t00_insereSimulacao() {
    // Insere simulação no sistema

        Conta conta = new Conta();
        conta.setCpf(CPF);
        conta.setEmail(EMAIL);
        conta.setNome(NOME);
        conta.setParcelas(PARCELAS);
        conta.setSeguro(SEGURO);
        conta.setValor(VALOR);
        given()
                .body(conta)
        .when()
                .post("simulacoes/")
        .then()
                .assertThat()
                .statusCode(201)
        ;
    }

    @Test
    public void t01_consultaSimulacao() {
    // Consulta Simulação cadastrada

        given()
        .when()
          .get("simulacoes/"+CPF+"")
        .then()
          .log().all()
          .statusCode(200)
          .body("cpf", is(CPF))
          .body("nome", is(NOME))
          .body("email", is(EMAIL))
          .body("seguro", is(SEGURO))
          .body("parcelas", is(PARCELAS))
        ;
    }

    @Test
    public void t02_consultaTodasSimulacoes() {
    // Procura simulação cadastrada entre todas existentes

        given()
        .when()
           .get("simulacoes/")
           .then()
                .assertThat()
                .statusCode(200)
                .log().all()
                .statusCode(200)
                .body(containsString(CPF))
        ;

    }
    @Test
    public void t03_alteraSimulacao() {
    // Altera Simulação cadastrada

        Conta conta = new Conta();
        conta.setCpf(CPF_ALT);
        conta.setEmail(EMAIL_ALT);
        conta.setNome(NOME_ALT);
        conta.setParcelas(PARCELAS_ALT);
        conta.setSeguro(SEGURO_ALT);
        conta.setValor(VALOR_ALT);

        given()
          .body(conta)
        .when()
           .put("simulacoes/"+CPF+"")
        .then()
            .statusCode(200)
        ;

    }

    @Test
    public void t04_consultaSimulacaoAlterada() {
        // Consulta Simulação cadastrada

        given()
        .when()
           .get("simulacoes/"+CPF_ALT+"")
        .then()
           .log().all()
           .statusCode(200)
           .body("cpf", is(CPF_ALT))
           .body("nome", is(NOME_ALT))
           .body("email", is(EMAIL_ALT))
           .body("seguro", is(SEGURO_ALT))
           .body("parcelas", is(PARCELAS_ALT))
           .extract().path("id")
        ;
    }
    // Seta o ID do JSON para o proximo teste
    public Integer getIdPeloCpf(String cpf) {
        return RestAssured.get("simulacoes/"+CPF_ALT+"").then().extract().path("id");
    }

    @Test
    public void t05_deletaSimulacao() {
    // Deleta Simulação cadastrada

        Integer ID = getIdPeloCpf(CPF_ALT);

        given()
            .pathParam("id", ID)
        .when()
           .delete("simulacoes/{id}")
        .then()
           .log().all()
           .statusCode(200)
        ;

    }

    @Test
    public void t06_consultaSimulacaoDeletada() {
        // Consulta Simulação deletada

        given()
                .when()
                .get("simulacoes/" + CPF_ALT + "")
                .then()
                .log().all()
                .statusCode(404)
                .body("mensagem", is("CPF " + CPF_ALT + " não encontrado"))
        ;
    }

}






