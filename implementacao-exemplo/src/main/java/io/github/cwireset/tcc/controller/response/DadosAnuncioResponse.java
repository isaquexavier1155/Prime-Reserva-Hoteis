package io.github.cwireset.tcc.controller.response;

import io.github.cwireset.tcc.domain.FormaPagamento;
import io.github.cwireset.tcc.domain.Imovel;
import io.github.cwireset.tcc.domain.Usuario;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DadosAnuncioResponse {

    private Long id;

    private Imovel imovel;

    private Usuario anunciante;

    private List<FormaPagamento> formasAceitas;

    private String descricao;

}
