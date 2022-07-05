package io.github.cwireset.tcc.service.anuncio;

import io.github.cwireset.tcc.domain.Anuncio;
import io.github.cwireset.tcc.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarAnuncioService {

    @Autowired
    private AnuncioRepository repository;

    public Page<Anuncio> listar(Pageable pageable) {
        return repository.findAllByExcluidoFalse(pageable);
    }

    public Page<Anuncio> listarPorAnunciante(Long idAnunciante, Pageable pageable) {
        return repository.findAllByAnuncianteIdAndExcluidoFalse(idAnunciante, pageable);
    }

}
