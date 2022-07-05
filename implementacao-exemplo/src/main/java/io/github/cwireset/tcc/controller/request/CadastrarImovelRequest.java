package io.github.cwireset.tcc.controller.request;

import io.github.cwireset.tcc.domain.CaracteristicaImovel;
import io.github.cwireset.tcc.domain.Endereco;
import io.github.cwireset.tcc.domain.TipoImovel;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CadastrarImovelRequest {

    @NotNull
    private TipoImovel tipoImovel;

    @Valid
    @NotNull
    private Endereco endereco;

    @NotBlank
    private String identificacao;

    @NotNull
    private Long idProprietario;

    private List<CaracteristicaImovel> caracteristicas;

}
