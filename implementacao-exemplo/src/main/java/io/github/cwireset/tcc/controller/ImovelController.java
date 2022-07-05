package io.github.cwireset.tcc.controller;

import io.github.cwireset.tcc.controller.request.CadastrarImovelRequest;
import io.github.cwireset.tcc.domain.Imovel;
import io.github.cwireset.tcc.service.imovel.BuscarImovelPorIdSservice;
import io.github.cwireset.tcc.service.imovel.CadastrarImovelService;
import io.github.cwireset.tcc.service.imovel.ExcluirImovelService;
import io.github.cwireset.tcc.service.imovel.ListarImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    @Autowired
    private CadastrarImovelService cadastrarImovelService;

    @Autowired
    private ListarImovelService listarImovelService;

    @Autowired
    private BuscarImovelPorIdSservice buscarImovelPorIdSservice;

    @Autowired
    private ExcluirImovelService excluirImovelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Imovel salvar(@RequestBody @Valid CadastrarImovelRequest cadastroImovelRequest) {
        return cadastrarImovelService.salvar(cadastroImovelRequest);
    }

    @GetMapping
    public Page<Imovel> listar(@PageableDefault(sort = "identificacao") @ApiIgnore Pageable pageable) {
        return listarImovelService.listar(pageable);
    }

    @GetMapping("/{id}")
    public Imovel buscar(@PathVariable("id") Long id) {
        return buscarImovelPorIdSservice.buscar(id);
    }

    @GetMapping("/proprietarios/{idProprietario}")
    public Page<Imovel> listarPorProprietario(@PageableDefault(sort = "identificacao") @ApiIgnore Pageable pageable, @PathVariable("idProprietario") Long idProprietario) {
        return listarImovelService.listarPorProprietario(pageable, idProprietario);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable("id") Long id) {
        excluirImovelService.excluir(id);
    }

}
