package io.github.cwireset.tcc.exception;

import io.github.cwireset.tcc.domain.TipoImovel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuantidadeMinimaPessoasInvalidoException extends RuntimeException {

    public QuantidadeMinimaPessoasInvalidoException(Integer minimoPessoas, TipoImovel tipoImovel) {
        super(String.format("Não é possivel realizar uma reserva com menos de %d pessoas para imóveis do tipo %s", minimoPessoas, tipoImovel.getDescricao()));
    }

}
