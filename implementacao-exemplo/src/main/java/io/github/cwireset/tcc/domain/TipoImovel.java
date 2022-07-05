package io.github.cwireset.tcc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoImovel {

    APARTAMENTO("Apartamento"),
    CASA("Casa"),
    HOTEL("Hotel"),
    POUSADA("Pousada");

    private String descricao;

}
