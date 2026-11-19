package br.com.thomaaasr.contadigital.conta;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ContaResponse(Long id, String numero, String titular, BigDecimal saldo, OffsetDateTime criadaEm) {

    public static ContaResponse de(Conta conta) {
        // O número da conta é o id com seis dígitos, por exemplo 000042.
        String numero = String.format("%06d", conta.getId());
        return new ContaResponse(conta.getId(), numero, conta.getTitular(), conta.getSaldo(), conta.getCriadaEm());
    }
}
