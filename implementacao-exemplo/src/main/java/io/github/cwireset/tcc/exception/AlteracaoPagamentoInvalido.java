package io.github.cwireset.tcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlteracaoPagamentoInvalido extends RuntimeException {

    public AlteracaoPagamentoInvalido(String message) {
        super(message);
    }
}
