package br.com.thomaaasr.contadigital.conta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContaService {

    private final ContaRepository repositorio;

    public ContaService(ContaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public Conta abrir(AbrirContaRequest dados) {
        return repositorio.save(new Conta(dados.titular().trim()));
    }

    @Transactional(readOnly = true)
    public Conta buscar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
    }
}
