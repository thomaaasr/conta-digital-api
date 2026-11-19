package br.com.thomaaasr.contadigital.conta;

public class ContaNaoEncontradaException extends RuntimeException {

    public ContaNaoEncontradaException(Long id) {
        super("Conta " + id + " não encontrada.");
    }
}
