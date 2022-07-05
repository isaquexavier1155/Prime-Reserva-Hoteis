package io.github.cwireset.tcc.service.imovel;

import io.github.cwireset.tcc.domain.Imovel;
import io.github.cwireset.tcc.exception.ImovelAnunciadoException;
import io.github.cwireset.tcc.repository.AnuncioRepository;
import io.github.cwireset.tcc.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcluirImovelService {

    @Autowired
    private ImovelRepository repository;

    @Autowired
    private BuscarImovelPorIdSservice buscarImovelPorIdSservice;

    @Autowired
    private AnuncioRepository anuncioRepository;

    public void excluir(Long id) {
        Long anunciosDoImovel = anuncioRepository.countByImovelId(id);
        if (anunciosDoImovel > 0) {
            throw new ImovelAnunciadoException();
        }

        Imovel imovel = buscarImovelPorIdSservice.buscar(id);
        repository.delete(imovel);
    }

}
