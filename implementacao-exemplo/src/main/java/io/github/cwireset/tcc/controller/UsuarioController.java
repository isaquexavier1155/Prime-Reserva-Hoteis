package io.github.cwireset.tcc.controller;

import io.github.cwireset.tcc.controller.request.AtualizarUsuarioRequest;
import io.github.cwireset.tcc.domain.Usuario;
import io.github.cwireset.tcc.service.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastrarUsuarioService cadastrarUsuarioService;

    @Autowired
    private BuscarUsuarioPorIdService buscarUsuarioPorIdService;

    @Autowired
    private ListarUsuariosService listarUsuariosService;

    @Autowired
    private AtualizarUsuarioService atualizarUsuarioService;

    @Autowired
    private BuscarUsuarioPorCpfService buscarUsuarioPorCpfService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        return cadastrarUsuarioService.salvar(usuario);
    }

    @GetMapping
    public Page<Usuario> listar(@PageableDefault(sort = "nome") @ApiIgnore Pageable pageable) {
        return listarUsuariosService.listar(pageable);
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable("id") Long id) {
        return buscarUsuarioPorIdService.buscar(id);
    }

    @GetMapping("/cpf/{cpf}")
    public Usuario buscar(@PathVariable("cpf") String cpf) {
        return buscarUsuarioPorCpfService.buscar(cpf);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable("id") Long id, @RequestBody @Valid AtualizarUsuarioRequest atualizarUsuarioRequest) {
        return atualizarUsuarioService.atualizar(id, atualizarUsuarioRequest);
    }

}
