package io.github.cwireset.tcc.repository;

import io.github.cwireset.tcc.domain.Reserva;
import io.github.cwireset.tcc.domain.StatusPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends PagingAndSortingRepository<Reserva, Long> {

    Page<Reserva> findAllBySolicitanteId(Long idSolicitante, Pageable pageable);

    Page<Reserva> findAllByAnuncioAnuncianteId(Long idAnunciante, Pageable pageable);

    Page<Reserva> findAllBySolicitanteIdAndPeriodoDataHoraInicialGreaterThanEqualAndPeriodoDataHoraFinalLessThanEqual(Long idSolicitante, LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal, Pageable pageable);

    Boolean existsByAnuncioIdAndPeriodo_DataHoraInicialLessThanEqualAndPeriodo_DataHoraFinalGreaterThanEqualAndPagamentoStatusNotIn(Long idAnuncio, LocalDateTime dataHoraFinal, LocalDateTime dataHoraInicial, List<StatusPagamento> status);

}
