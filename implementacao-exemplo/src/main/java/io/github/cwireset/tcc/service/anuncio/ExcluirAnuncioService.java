package io.github.cwireset.tcc.service.anuncio;

import io.github.cwireset.tcc.domain.Anuncio;
import io.github.cwireset.tcc.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcluirAnuncioService {

    @Autowired
    private AnuncioRepository repository;

    @Autowired
    private BuscarAnuncioPorIdService buscarAnuncioPorIdService;

    public void excluir(Long idAnuncio) {
        Anuncio anuncio = buscarAnuncioPorIdService.buscar(idAnuncio);
        anuncio.setExcluido(true);
        repository.save(anuncio);
    }

}
