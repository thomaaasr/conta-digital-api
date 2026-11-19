package br.com.thomaaasr.contadigital.erro;

import br.com.thomaaasr.contadigital.conta.ContaNaoEncontradaException;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Transforma as exceções em respostas HTTP no padrão Problem Details (RFC 9457).
// Herdar de ResponseEntityExceptionHandler já trata os erros comuns do Spring (JSON inválido, método errado...).
@RestControllerAdvice
public class TratadorDeErros extends ResponseEntityExceptionHandler {

    // Validação dos dados recebidos: junta as mensagens de cada campo.
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException erro, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String mensagens = erro.getBindingResult().getFieldErrors().stream()
                .map(campo -> campo.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(" "));
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, mensagens);
        return ResponseEntity.badRequest().body(problema);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ProblemDetail contaNaoEncontrada(ContaNaoEncontradaException erro) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, erro.getMessage());
    }
}
