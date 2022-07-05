package io.github.cwireset.tcc.repository;

import io.github.cwireset.tcc.domain.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImovelRepository extends PagingAndSortingRepository<Imovel, Long> {

    Page<Imovel> findAllByProprietarioId(Pageable pageable, Long idProprietario);

}
