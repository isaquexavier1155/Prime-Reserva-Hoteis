package io.github.cwireset.tcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecursoJaExistenteException extends RuntimeException {

    public RecursoJaExistenteException(Class clazz, Object id, String descricao) {
        super(String.format("Já existe um recurso do tipo %s com %s com o valor '%s'.", clazz.getSimpleName(), descricao, id));
    }

    public RecursoJaExistenteException(String message) {
        super(message);
    }

}
