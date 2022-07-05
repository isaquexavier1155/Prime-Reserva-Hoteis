package io.github.cwireset.tcc.service.anuncio;

import io.github.cwireset.tcc.controller.request.CadastrarAnuncioRequest;
import io.github.cwireset.tcc.domain.Anuncio;
import io.github.cwireset.tcc.domain.Imovel;
import io.github.cwireset.tcc.domain.Usuario;
import io.github.cwireset.tcc.exception.RecursoJaExistenteException;
import io.github.cwireset.tcc.repository.AnuncioRepository;
import io.github.cwireset.tcc.service.imovel.BuscarImovelPorIdSservice;
import io.github.cwireset.tcc.service.usuario.BuscarUsuarioPorIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarAnuncioService {

    @Autowired
    private AnuncioRepository repository;

    @Autowired
    private BuscarUsuarioPorIdService buscarUsuarioPorIdService;

    @Autowired
    private BuscarImovelPorIdSservice buscarImovelPorIdSservice;

    public Anuncio salvar(CadastrarAnuncioRequest cadastrarAnuncioRequest) {
        if (repository.existsByImovelIdAndExcluidoFalse(cadastrarAnuncioRequest.getIdImovel())) {
            throw new RecursoJaExistenteException(Anuncio.class, cadastrarAnuncioRequest.getIdImovel(), "IdImovel");
        }

        Imovel imovel = buscarImovelPorIdSservice.buscar(cadastrarAnuncioRequest.getIdImovel());
        Usuario anunciante = buscarUsuarioPorIdService.buscar(cadastrarAnuncioRequest.getIdAnunciante());

        Anuncio anuncio = Anuncio.builder()
                .formasAceitas(cadastrarAnuncioRequest.getFormasAceitas())
                .descricao(cadastrarAnuncioRequest.getDescricao())
                .valorDiaria(cadastrarAnuncioRequest.getValorDiaria())
                .tipoAnuncio(cadastrarAnuncioRequest.getTipoAnuncio())
                .anunciante(anunciante)
                .imovel(imovel)
                .excluido(Boolean.FALSE)
                .build();

        return repository.save(anuncio);
    }

}
