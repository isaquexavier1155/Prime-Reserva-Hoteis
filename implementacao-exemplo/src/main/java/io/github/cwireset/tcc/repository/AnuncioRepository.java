package io.github.cwireset.tcc.repository;

import io.github.cwireset.tcc.domain.Anuncio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnuncioRepository extends PagingAndSortingRepository<Anuncio, Long> {

    boolean existsByImovelIdAndExcluidoFalse(Long idImovel);

    Page<Anuncio> findAllByAnuncianteIdAndExcluidoFalse(Long idAnunciante, Pageable pageable);

    Page<Anuncio> findAllByExcluidoFalse(Pageable pageable);

    Long countByImovelId(Long id);
}
