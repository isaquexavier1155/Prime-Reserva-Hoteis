package io.github.cwireset.tcc.controller;

import io.github.cwireset.tcc.controller.request.CadastrarReservaRequest;
import io.github.cwireset.tcc.controller.response.InformacaoReservaResponse;
import io.github.cwireset.tcc.domain.FormaPagamento;
import io.github.cwireset.tcc.domain.Periodo;
import io.github.cwireset.tcc.domain.Reserva;
import io.github.cwireset.tcc.service.reserva.CadastrarReservaService;
import io.github.cwireset.tcc.service.reserva.ListarReservaService;
import io.github.cwireset.tcc.service.reserva.PagamentoReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private CadastrarReservaService cadastrarReservaService;

    @Autowired
    private ListarReservaService listarReservaService;

    @Autowired
    private PagamentoReservaService pagamentoReservaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InformacaoReservaResponse salvar(@RequestBody @Valid CadastrarReservaRequest request) {
        return cadastrarReservaService.salvar(request);
    }

    @GetMapping("/solicitantes/{idSolicitante}")
    public Page<Reserva> listarPorSolicitante(@PathVariable("idSolicitante") Long idSolicitante, Periodo periodo, @PageableDefault(sort = "periodo.dataHoraFinal", direction = Direction.DESC) @ApiIgnore Pageable pageable) {
        return listarReservaService.listarPorSolicitante(idSolicitante, periodo, pageable);
    }

    @GetMapping("/anuncios/anunciantes/{idAnunciante}")
    public Page<Reserva> listarPorAnunciante(@PathVariable("idAnunciante") Long idAnunciante, @PageableDefault(sort = "periodo.dataHoraFinal", direction = Direction.DESC) @ApiIgnore Pageable pageable) {
        return listarReservaService.listarPorAnunciante(idAnunciante, pageable);
    }

    @PutMapping("/{idReserva}/pagamentos")
    public void pagar(@PathVariable("idReserva") Long idReserva, @RequestBody FormaPagamento formaPagamento) {
        pagamentoReservaService.pagar(idReserva, formaPagamento);
    }

    @PutMapping("/{idReserva}/pagamentos/cancelar")
    public void cancelar(@PathVariable("idReserva") Long idReserva) {
        pagamentoReservaService.cancelar(idReserva);
    }

    @PutMapping("/{idReserva}/pagamentos/estornar")
    public void estornar(@PathVariable("idReserva") Long idReserva) {
        pagamentoReservaService.estornar(idReserva);
    }

}
