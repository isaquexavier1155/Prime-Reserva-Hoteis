package io.github.cwireset.tcc.service.imovel;

import io.github.cwireset.tcc.domain.Imovel;
import io.github.cwireset.tcc.exception.RecursoNaoEncontradoException;
import io.github.cwireset.tcc.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarImovelPorIdSservice {

    @Autowired
    private ImovelRepository repository;

    public Imovel buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(Imovel.class, id, "Id"));
    }

}
