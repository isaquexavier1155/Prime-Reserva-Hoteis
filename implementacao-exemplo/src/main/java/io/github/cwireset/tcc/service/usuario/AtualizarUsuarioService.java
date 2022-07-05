package io.github.cwireset.tcc.service.usuario;

import io.github.cwireset.tcc.controller.request.AtualizarUsuarioRequest;
import io.github.cwireset.tcc.domain.Usuario;
import io.github.cwireset.tcc.exception.RecursoJaExistenteException;
import io.github.cwireset.tcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BuscarUsuarioPorIdService buscarUsuarioPorIdService;

    @Autowired
    private ExisteUsuarioMesmoEmailService existeUsuarioMesmoEmailService;

    public Usuario atualizar(Long id, AtualizarUsuarioRequest atualizarUsuarioRequest) {
        if (existeUsuarioMesmoEmailService.existeComIdDiferente(atualizarUsuarioRequest.getEmail(), id)) {
            throw new RecursoJaExistenteException(Usuario.class, atualizarUsuarioRequest.getEmail(), "E-Mail");
        }

        Usuario usuarioAtual = buscarUsuarioPorIdService.buscar(id);

        usuarioAtual.setEmail(atualizarUsuarioRequest.getEmail());
        usuarioAtual.setNome(atualizarUsuarioRequest.getNome());
        usuarioAtual.setSenha(atualizarUsuarioRequest.getSenha());
        usuarioAtual.setDataNascimento(atualizarUsuarioRequest.getDataNascimento());

        if (atualizarUsuarioRequest.getEndereco() != null) {
            usuarioAtual.getEndereco().setBairro(atualizarUsuarioRequest.getEndereco().getBairro());
            usuarioAtual.getEndereco().setCep(atualizarUsuarioRequest.getEndereco().getCep());
            usuarioAtual.getEndereco().setComplemento(atualizarUsuarioRequest.getEndereco().getComplemento());
            usuarioAtual.getEndereco().setCidade(atualizarUsuarioRequest.getEndereco().getCidade());
            usuarioAtual.getEndereco().setEstado(atualizarUsuarioRequest.getEndereco().getEstado());
            usuarioAtual.getEndereco().setLogradouro(atualizarUsuarioRequest.getEndereco().getLogradouro());
            usuarioAtual.getEndereco().setNumero(atualizarUsuarioRequest.getEndereco().getNumero());
        } else {
            usuarioAtual.setEndereco(null);
        }

        return repository.save(usuarioAtual);
    }

}
