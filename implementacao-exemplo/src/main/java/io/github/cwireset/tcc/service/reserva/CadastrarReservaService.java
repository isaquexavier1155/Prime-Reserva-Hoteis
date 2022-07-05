package io.github.cwireset.tcc.service.reserva;

import io.github.cwireset.tcc.controller.request.CadastrarReservaRequest;
import io.github.cwireset.tcc.controller.response.DadosAnuncioResponse;
import io.github.cwireset.tcc.controller.response.DadosSolicitanteResponse;
import io.github.cwireset.tcc.controller.response.InformacaoReservaResponse;
import io.github.cwireset.tcc.domain.*;
import io.github.cwireset.tcc.exception.*;
import io.github.cwireset.tcc.repository.ReservaRepository;
import io.github.cwireset.tcc.service.anuncio.BuscarAnuncioPorIdService;
import io.github.cwireset.tcc.service.usuario.BuscarUsuarioPorIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

@Service
public class CadastrarReservaService {

    private static final Integer MINIMO_DIARIAS_PARA_POUSADAS = 5;
    private static final Integer MINIMO_PESSOAS_PARA_HOTEIS = 2;
    private static final Integer QUANTIDADE_MINIMA_DIARIAS = 1;

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private BuscarAnuncioPorIdService buscarAnuncioPorIdService;

    @Autowired
    private BuscarUsuarioPorIdService buscarUsuarioPorIdService;

    public InformacaoReservaResponse salvar(CadastrarReservaRequest cadastrarReservaRequest) {
        defineHorarioDeEntradaESaida(cadastrarReservaRequest.getPeriodo());
        validarDatasPeriodo(cadastrarReservaRequest.getPeriodo());

        validarAnuncioDisponivel(cadastrarReservaRequest.getIdAnuncio(), cadastrarReservaRequest.getPeriodo());

        Anuncio anuncio = buscarAnuncioPorIdService.buscar(cadastrarReservaRequest.getIdAnuncio());
        validarMinimoPessoas(anuncio.getImovel().getTipoImovel(), cadastrarReservaRequest.getQuantidadePessoas());
        validarMinimoDiarias(anuncio.getImovel().getTipoImovel(), cadastrarReservaRequest.getPeriodo());
        validarAnuncianteMesmoSolicitante(cadastrarReservaRequest, anuncio.getAnunciante());

        Usuario solicitante = buscarUsuarioPorIdService.buscar(cadastrarReservaRequest.getIdSolicitante());

        Pagamento pagamento = Pagamento.builder()
                .status(StatusPagamento.PENDENTE)
                .valorTotal(calcularValorReserva(cadastrarReservaRequest.getPeriodo(), anuncio.getValorDiaria()))
                .build();

        Reserva reserva = Reserva.builder()
                .quantidadePessoas(cadastrarReservaRequest.getQuantidadePessoas())
                .dataHoraReserva(LocalDateTime.now())
                .periodo(cadastrarReservaRequest.getPeriodo())
                .pagamento(pagamento)
                .anuncio(anuncio)
                .solicitante(solicitante)
                .build();

        reserva = repository.save(reserva);

        InformacaoReservaResponse informacaoReservaResponse = montarResponse(reserva);

        return informacaoReservaResponse;
    }

    private void defineHorarioDeEntradaESaida(Periodo periodo) {
        periodo.setDataHoraInicial(periodo.getDataHoraInicial()
                .with(ChronoField.HOUR_OF_DAY, 14)
                .with(ChronoField.MINUTE_OF_HOUR, 0)
                .with(ChronoField.SECOND_OF_MINUTE, 0));

        periodo.setDataHoraFinal(periodo.getDataHoraFinal()
                .with(ChronoField.HOUR_OF_DAY, 12)
                .with(ChronoField.MINUTE_OF_HOUR, 0)
                .with(ChronoField.SECOND_OF_MINUTE, 0));
    }

    private InformacaoReservaResponse montarResponse(Reserva reserva) {
        DadosAnuncioResponse anuncioResponse = DadosAnuncioResponse.builder()
                .id(reserva.getAnuncio().getId())
                .anunciante(reserva.getAnuncio().getAnunciante())
                .descricao(reserva.getAnuncio().getDescricao())
                .formasAceitas(reserva.getAnuncio().getFormasAceitas())
                .imovel(reserva.getAnuncio().getImovel())
                .build();

        DadosSolicitanteResponse solicitanteResponse = DadosSolicitanteResponse.builder()
                .id(reserva.getSolicitante().getId())
                .nome(reserva.getSolicitante().getNome())
                .build();

        InformacaoReservaResponse informacaoReservaResponse = InformacaoReservaResponse.builder()
                .idReserva(reserva.getId())
                .anuncio(anuncioResponse)
                .solicitante(solicitanteResponse)
                .quantidadePessoas(reserva.getQuantidadePessoas())
                .pagamento(reserva.getPagamento())
                .periodo(reserva.getPeriodo())
                .build();
        return informacaoReservaResponse;
    }

    private void validarAnuncianteMesmoSolicitante(CadastrarReservaRequest cadastrarReservaRequest, Usuario anunciante) {
        if (cadastrarReservaRequest.getIdSolicitante().equals(anunciante.getId())) {
            throw new AnuncianteNaoPodeReservarException();
        }
    }

    private void validarMinimoDiarias(TipoImovel tipoImovel, Periodo periodo) {
        if (TipoImovel.POUSADA.equals(tipoImovel)
                && periodo.quantidadeDiasNoPeriodo() < MINIMO_DIARIAS_PARA_POUSADAS) {
            throw new QuantidadeMinimaDiariasInvalidoException(MINIMO_DIARIAS_PARA_POUSADAS, tipoImovel);
        }
    }

    private void validarMinimoPessoas(TipoImovel tipoImovel, Integer quantidadePessoas) {
        if (TipoImovel.HOTEL.equals(tipoImovel) && quantidadePessoas < MINIMO_PESSOAS_PARA_HOTEIS) {
            throw new QuantidadeMinimaPessoasInvalidoException(MINIMO_PESSOAS_PARA_HOTEIS, tipoImovel);
        }
    }

    private void validarAnuncioDisponivel(Long idAnuncio, Periodo periodo) {
        List<StatusPagamento> statusPagamentos = Arrays.asList(StatusPagamento.CANCELADO, StatusPagamento.ESTORNADO);

        Boolean possuiSobreposicao = repository.existsByAnuncioIdAndPeriodo_DataHoraInicialLessThanEqualAndPeriodo_DataHoraFinalGreaterThanEqualAndPagamentoStatusNotIn(idAnuncio, periodo.getDataHoraFinal(), periodo.getDataHoraInicial(), statusPagamentos);
        if (possuiSobreposicao) {
            throw new AnuncioJaReservadoException();
        }
    }

    private void validarDatasPeriodo(Periodo periodo) {
        if (!periodo.dataFinalMaiorQueInicial()) {
            throw new PeriodoInvalidoException("A data final da reserva precisa ser maior do que a data inicial.");
        }

        if (periodo.quantidadeDiasNoPeriodo() < QUANTIDADE_MINIMA_DIARIAS) {
            throw new PeriodoInvalidoException(String.format("O número mínimo de diárias precisa ser maior ou igual à %d.", QUANTIDADE_MINIMA_DIARIAS));
        }
    }

    private BigDecimal calcularValorReserva(Periodo periodo, BigDecimal valorDiaria) {
        return valorDiaria.multiply(BigDecimal.valueOf(periodo.quantidadeDiasNoPeriodo()));
    }

}
