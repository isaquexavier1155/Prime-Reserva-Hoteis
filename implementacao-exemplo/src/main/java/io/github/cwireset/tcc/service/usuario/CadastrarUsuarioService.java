package io.github.cwireset.tcc.service.usuario;

import io.github.cwireset.tcc.domain.Usuario;
import io.github.cwireset.tcc.exception.RecursoJaExistenteException;
import io.github.cwireset.tcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ExisteUsuarioMesmoCpfService existeUsuarioMesmoCpfService;

    @Autowired
    private ExisteUsuarioMesmoEmailService existeUsuarioMesmoEmailService;

    public Usuario salvar(Usuario usuario) {
        valida(usuario);

        return repository.save(usuario);
    }

    private void valida(Usuario usuario) {
        if (existeUsuarioMesmoCpfService.existe(usuario.getCpf())) {
            throw new RecursoJaExistenteException(Usuario.class, usuario.getCpf(), "CPF");
        }

        if (existeUsuarioMesmoEmailService.existe(usuario.getEmail())) {
            throw new RecursoJaExistenteException(Usuario.class, usuario.getEmail(), "E-Mail");
        }
    }

}
