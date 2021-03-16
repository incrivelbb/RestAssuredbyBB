package br.com.brunomartinezbarbeiro.rest.testes.UnhappyPath;

import br.com.brunomartinezbarbeiro.rest.testes.Conta;
import br.com.brunomartinezbarbeiro.rest.core.BaseTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExcecoesSimulacao extends BaseTest {

    //dados para primeiro fluxo
    public static String CPF = "39392800040";
    public static String EMAIL = "bruno@test.com";
    private static String NOME = "Bruno Test";
    private static Integer PARCELAS = 24;
    private static Boolean SEGURO = true;
    private static Number VALOR = 1000.0;

    // CPF inválido
    public static String CPF_INV = "78945612323";

    @Test
    public void t00_insereSimulacaoFaltandoInfo() {
        // Validação de erro ao inserir simulação com campos faltantes

        Conta conta = new Conta();
        conta.setCpf(null);
        conta.setEmail(null);
        conta.setNome(null);
        conta.setParcelas(null);
        conta.setSeguro(false); //ao jogar esse parametro como null foi encontrado um erro 500!
        conta.setValor(null);
        given()
           .body(conta)
        .when()
           .log().all()
           .post("simulacoes/")
        .then()
            .statusCode(400)
            .body(containsString("erros"))
        ;
    }

    @Test
    public void t01_pesquisaSimulacaoInexistente() {
        // Insere simulação no sistema

        given()
        .when()
            .log().all()
            .get("simulacoes/"+CPF_INV+"")
        .then()
             .statusCode(404)
             .body(containsString("CPF "+CPF_INV+" não encontrado"))
        ;
    }

    @Test
    public void t02_modificaSimulacaoInexistente() {
        // Insere simulação no sistema

        given()
        .when()
           .log().all()
           .put("simulacoes/"+CPF_INV+"")
        .then()
           .statusCode(404) // Aqui foi encontrado um erro de tratamento de exceção
           .body(containsString("CPF "+CPF_INV+" não encontrado"))
        ;
    }

    @Test
    public void t03_deletaSimulacaoInexistente() {
        // Insere simulação no sistema

        given()
        .when()
           .log().all()
           .delete("simulacoes/"+CPF_INV+"")
        .then()
           .statusCode(404) // Aqui foi encontrado ausência de tratamento de exceção (retorna status 200)
           .body(containsString("CPF "+CPF_INV+" não encontrado"))
        ;
    }

    @Test
    public void t04_insereSimulacaoParcelaMenor() {
        // Insere simulação no sistema

        Conta conta = new Conta();
        conta.setCpf(CPF);
        conta.setEmail(EMAIL);
        conta.setNome(NOME);
        conta.setParcelas(1);
        conta.setSeguro(SEGURO);
        conta.setValor(VALOR);

        given()
            .body(conta)
        .when()
            .post("simulacoes/")
        .then()
            .assertThat()
            .statusCode(400)
        ;
    }

    @Test
    public void t05_insereSimulacaoParcelaMaior() {

        Conta conta = new Conta();
        conta.setCpf(CPF);
        conta.setEmail(EMAIL);
        conta.setNome(NOME);
        conta.setParcelas(49);
        conta.setSeguro(SEGURO);
        conta.setValor(VALOR);

        given()
           .body(conta)
        .when()
            .log().all()
            .post("simulacoes/")
        .then()
            .log().all()
            .statusCode(400)
        ;

    }

    @Test
    public void t06_insereSimulacaoValorMenor() {
        // Insere simulação no sistema

        Conta conta = new Conta();
        conta.setCpf(CPF);
        conta.setEmail(EMAIL);
        conta.setNome(NOME);
        conta.setParcelas(PARCELAS);
        conta.setSeguro(SEGURO);
        conta.setValor(999);

        given()
           .body(conta)
        .when()
           .log().all()
           .post("simulacoes/")
        .then()
           .log().all()
           .statusCode(400)
           .body("erros.valor", containsString("Valor deve ser maior ou igual a R$ 1.000"))
        ;
    }

    @Test
    public void t07_insereSimulacaoValorMaior() {
        // Insere simulação no sistema

        Conta conta = new Conta();
        conta.setCpf(CPF);
        conta.setEmail(EMAIL);
        conta.setNome(NOME);
        conta.setParcelas(PARCELAS);
        conta.setSeguro(SEGURO);
        conta.setValor(40001);

        given()
            .body(conta)
        .when()
            .log().all()
            .post("simulacoes/")
        .then()
            .log().all()
            .statusCode(400)
            .body("erros.valor", containsString("Valor deve ser menor ou igual a R$ 40.000"))

         ;
    }
}





