package io.github.cwireset.tcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@Spackage io.github.cwireset.tcc.exception;


@ResponseStatus(HttpStatus.NOT_FOUND)
class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(Class clazz, Object id, String descricao) {
        super(String.format("Nenhum(a) %s com %s com o valor '%s' foi encontrado.", clazz.getSimpleName(), descricao, id));
    }

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
