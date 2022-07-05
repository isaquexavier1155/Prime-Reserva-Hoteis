package io.github.cwireset.tcc.service.reserva;

import io.github.cwireset.tcc.domain.Reserva;
import io.github.cwireset.tcc.exception.RecursoNaoEncontradoException;
import io.github.cwireset.tcc.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarReservaPorIdService {

    @Autowired
    private ReservaRepository repository;

    public Reserva buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(Reserva.class, id, "Id"));
    }


}
