package io.github.cwireset.tcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AnuncioJaReservadoException extends RuntimeException {

    public AnuncioJaReservadoException() {
        super("Este anuncio já esta reservado para o período informado.");
    }
}
