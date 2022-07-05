package io.github.cwireset.tcc.service.reserva;

import io.github.cwireset.tcc.domain.FormaPagamento;
import io.github.cwireset.tcc.domain.Reserva;
import io.github.cwireset.tcc.domain.StatusPagamento;
import io.github.cwireset.tcc.exception.AlteracaoPagamentoInvalido;
import io.github.cwireset.tcc.exception.FormaPagamentoNaoAceitaException;
import io.github.cwireset.tcc.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private BuscarReservaPorIdService buscarReservaPorIdService;

    public void pagar(Long idReserva, FormaPagamento formaPagamento) {
        Reserva reserva = buscarReservaPorIdService.buscar(idReserva);

        if (!formaPagamentoAceita(formaPagamento, reserva)) {
            throw new FormaPagamentoNaoAceitaException(formaPagamento, reserva.getAnuncio().getFormasAceitas());
        }

        if (!pagamentoPodeSerRealizado(reserva)) {
            throw new AlteracaoPagamentoInvalido("Não é possível realizar o pagamento para esta reserva, pois ela não está no status PENDENTE.");
        }

        reserva.getPagamento().setFormaEscolhida(formaPagamento);
        reserva.getPagamento().setStatus(StatusPagamento.PAGO);
        repository.save(reserva);
    }

    private boolean formaPagamentoAceita(FormaPagamento formaPagamento, Reserva reserva) {
        return reserva.getAnuncio().getFormasAceitas().contains(formaPagamento);
    }

    private boolean pagamentoPodeSerRealizado(Reserva reserva) {
        return reserva.getPagamento().getStatus() == StatusPagamento.PENDENTE;
    }

    public void cancelar(Long idReserva) {
        Reserva reserva = buscarReservaPorIdService.buscar(idReserva);

        if (!cancelamentoPodeSerRealizado(reserva)) {
            throw new AlteracaoPagamentoInvalido("Não é possível realizar o cancelamento para esta reserva, pois ela não está no status PENDENTE.");
        }

        reserva.getPagamento().setStatus(StatusPagamento.CANCELADO);
        repository.save(reserva);
    }

    private boolean cancelamentoPodeSerRealizado(Reserva reserva) {
        return reserva.getPagamento().getStatus() == StatusPagamento.PENDENTE;
    }

    public void estornar(Long idReserva) {
        Reserva reserva = buscarReservaPorIdService.buscar(idReserva);

        if (!estornoPodeSerRealizado(reserva)) {
            throw new AlteracaoPagamentoInvalido("Não é possível realizar o estorno para esta reserva, pois ela não está no status PAGO.");
        }

        reserva.getPagamento().setStatus(StatusPagamento.ESTORNADO);
        reserva.getPagamento().setFormaEscolhida(null);
        repository.save(reserva);
    }

    private boolean estornoPodeSerRealizado(Reserva reserva) {
        return reserva.getPagamento().getStatus() == StatusPagamento.PAGO;
    }

}
