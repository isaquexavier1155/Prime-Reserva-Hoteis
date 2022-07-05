package io.github.cwireset.tcc.service.imovel;

import io.github.cwireset.tcc.controller.request.CadastrarImovelRequest;
import io.github.cwireset.tcc.domain.Imovel;
import io.github.cwireset.tcc.domain.Usuario;
import io.github.cwireset.tcc.repository.ImovelRepository;
import io.github.cwireset.tcc.service.usuario.BuscarUsuarioPorIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarImovelService {

    @Autowired
    private ImovelRepository repository;

    @Autowired
    private BuscarUsuarioPorIdService buscarUsuarioPorIdService;

    public Imovel salvar(CadastrarImovelRequest cadastrarImovelRequest) {
        Usuario proprietario = buscarUsuarioPorIdService.buscar(cadastrarImovelRequest.getIdProprietario());

        Imovel imovel = Imovel.builder()
                .caracteristicas(cadastrarImovelRequest.getCaracteristicas())
                .proprietario(proprietario)
                .endereco(cadastrarImovelRequest.getEndereco())
                .tipoImovel(cadastrarImovelRequest.getTipoImovel())
                .identificacao(cadastrarImovelRequest.getIdentificacao())
                .build();

        return repository.save(imovel);
    }

}
