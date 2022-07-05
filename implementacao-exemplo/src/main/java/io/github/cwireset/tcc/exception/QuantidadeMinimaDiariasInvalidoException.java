package io.github.cwireset.tcc.exception;

import io.github.cwireset.tcc.domain.TipoImovel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuantidadeMinimaDiariasInvalidoException extends RuntimeException {

    public QuantidadeMinimaDiariasInvalidoException(Integer minimoDiarias, TipoImovel tipoImovel) {
        super(String.format("Não é possivel realizar uma reserva com menos de %d diárias para imóveis do tipo %s", minimoDiarias, tipoImovel.getDescricao()));
    }

}
