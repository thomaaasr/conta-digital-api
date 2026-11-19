package br.com.thomaaasr.contadigital.conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AbrirContaRequest(
        @NotBlank(message = "Informe o nome do titular.")
                @Size(min = 3, max = 120, message = "O nome do titular deve ter entre 3 e 120 caracteres.")
                String titular) {}
                