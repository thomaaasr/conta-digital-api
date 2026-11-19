package br.com.thomaaasr.contadigital.conta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titular;

    // BigDecimal, e não double: dinheiro não pode ter erro de arredondamento.
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo = new BigDecimal("0.00");

    @Column(name = "criada_em", nullable = false, updatable = false)
    private OffsetDateTime criadaEm;

    // Construtor vazio exigido pelo JPA.
    protected Conta() {}

    public Conta(String titular) {
        this.titular = titular;
        this.criadaEm = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public Long getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public OffsetDateTime getCriadaEm() {
        return criadaEm;
    }
}
