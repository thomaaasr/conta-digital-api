package br.com.thomaaasr.contadigital.conta;

import org.springframework.data.jpa.repository.JpaRepository;

// O Spring Data cria a implementação sozinho: save, findById, findAll...
public interface ContaRepository extends JpaRepository<Conta, Long> {}
