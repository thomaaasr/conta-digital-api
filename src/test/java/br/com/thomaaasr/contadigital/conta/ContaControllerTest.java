package br.com.thomaaasr.contadigital.conta;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.thomaaasr.contadigital.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContaRepository repositorio;

    @BeforeEach
    void limparBanco() {
        repositorio.deleteAll();
    }

    @Test
    void abreContaComSaldoZero() throws Exception {
        mockMvc.perform(post("/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titular": "Ana Souza"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.titular").value("Ana Souza"))
                .andExpect(jsonPath("$.saldo").value(0))
                .andExpect(jsonPath("$.numero").isString())
                .andExpect(jsonPath("$.criadaEm").isNotEmpty());
    }

    @Test
    void recusaTitularEmBranco() throws Exception {
        mockMvc.perform(post("/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titular": "  "}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(containsString("Informe o nome do titular.")));
    }

    @Test
    void consultaContaPeloId() throws Exception {
        Conta conta = repositorio.save(new Conta("Bruno Costa"));

        mockMvc.perform(get("/contas/{id}", conta.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titular").value("Bruno Costa"))
                .andExpect(jsonPath("$.numero").value(String.format("%06d", conta.getId())));
    }

    @Test
    void contaInexistenteRetorna404ComMensagem() throws Exception {
        mockMvc.perform(get("/contas/{id}", 999999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Conta 999999 não encontrada."));
    }
}
