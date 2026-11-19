package br.com.thomaaasr.contadigital.conta;

import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService servico;

    public ContaController(ContaService servico) {
        this.servico = servico;
    }

    @PostMapping
    public ResponseEntity<ContaResponse> abrir(@Valid @RequestBody AbrirContaRequest dados) {
        ContaResponse conta = ContaResponse.de(servico.abrir(dados));
        return ResponseEntity.created(URI.create("/contas/" + conta.id())).body(conta);
    }

    @GetMapping("/{id}")
    public ContaResponse buscar(@PathVariable Long id) {
        return ContaResponse.de(servico.buscar(id));
    }
}
