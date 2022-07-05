package io.github.cwireset.tcc.controller;

import io.github.cwireset.tcc.controller.request.CadastrarAnuncioRequest;
import io.github.cwireset.tcc.domain.Anuncio;
import io.github.cwireset.tcc.service.anuncio.CadastrarAnuncioService;
import io.github.cwireset.tcc.service.anuncio.ExcluirAnuncioService;
import io.github.cwireset.tcc.service.anuncio.ListarAnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private CadastrarAnuncioService cadastrarAnuncioService;

    @Autowired
    private ListarAnuncioService listarAnuncioService;

    @Autowired
    private ExcluirAnuncioService excluirAnuncioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Anuncio salvar(@RequestBody @Valid CadastrarAnuncioRequest cadastrarAnuncioRequest) {
        return cadastrarAnuncioService.salvar(cadastrarAnuncioRequest);
    }

    @GetMapping
    public Page<Anuncio> listar(@PageableDefault(sort = "valorDiaria") @ApiIgnore Pageable pageable) {
        return listarAnuncioService.listar(pageable);
    }

    @GetMapping("/anunciantes/{idAnunciante}")
    public Page<Anuncio> listarPorAnunciante(@PathVariable("idAnunciante") Long idAnunciante, @PageableDefault(sort = "valorDiaria") @ApiIgnore Pageable pageable) {
        return listarAnuncioService.listarPorAnunciante(idAnunciante, pageable);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable("id") Long id) {
        excluirAnuncioService.excluir(id);
    }

}
