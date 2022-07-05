package io.github.cwireset.tcc.service.imovel;

import io.github.cwireset.tcc.domain.Imovel;
import io.github.cwireset.tcc.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarImovelService {

    @Autowired
    private ImovelRepository repository;

    public Page<Imovel> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Imovel> listarPorProprietario(Pageable pageable, Long idProprietario) {
        return repository.findAllByProprietarioId(pageable, idProprietario);
    }

}
