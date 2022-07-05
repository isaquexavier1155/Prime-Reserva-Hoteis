package io.github.cwireset.tcc.service.reserva;

import io.github.cwireset.tcc.domain.Periodo;
import io.github.cwireset.tcc.domain.Reserva;
import io.github.cwireset.tcc.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarReservaService {

    @Autowired
    private ReservaRepository repository;

    public Page<Reserva> listarPorSolicitante(Long idSolicitante, Periodo periodo, Pageable pageable) {
        if (periodo == null || periodo.getDataHoraInicial() == null || periodo.getDataHoraFinal() == null) {
            return repository.findAllBySolicitanteId(idSolicitante, pageable);
        }

        return repository.findAllBySolicitanteIdAndPeriodoDataHoraInicialGreaterThanEqualAndPeriodoDataHoraFinalLessThanEqual(idSolicitante, periodo.getDataHoraInicial(), periodo.getDataHoraFinal(), pageable);
    }

    public Page<Reserva> listarPorAnunciante(Long idAnunciante, Pageable pageable) {
        return repository.findAllByAnuncioAnuncianteId(idAnunciante, pageable);
    }

}
