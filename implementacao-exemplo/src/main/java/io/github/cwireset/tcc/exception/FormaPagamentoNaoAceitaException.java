package io.github.cwireset.tcc.exception;

import io.github.cwireset.tcc.domain.FormaPagamento;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoNaoAceitaException extends RuntimeException {
    public FormaPagamentoNaoAceitaException(FormaPagamento formaPagamento, List<FormaPagamento> formasAceitas) {
        super(String.format("O anúncio não aceita %s como forma de pagamento. As formas aceitas são %s.", formaPagamento,
                formasAceitas.stream().map(Object::toString).collect(Collectors.joining(", "))));

    }
}
