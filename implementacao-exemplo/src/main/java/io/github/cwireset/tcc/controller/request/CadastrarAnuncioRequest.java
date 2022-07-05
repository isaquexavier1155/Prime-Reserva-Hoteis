package io.github.cwireset.tcc.controller.request;

import io.github.cwireset.tcc.domain.FormaPagamento;
import io.github.cwireset.tcc.domain.TipoAnuncio;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class CadastrarAnuncioRequest {

    @NotNull
    private Long idImovel;

    @NotNull
    private Long idAnunciante;

    @NotNull
    private BigDecimal valorDiaria;

    @NotEmpty
    private List<FormaPagamento> formasAceitas;

    @NotBlank
    private String descricao;

    @NotNull
    private TipoAnuncio tipoAnuncio;

}
