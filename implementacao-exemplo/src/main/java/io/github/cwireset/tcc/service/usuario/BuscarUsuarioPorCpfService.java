package io.github.cwireset.tcc.service.usuario;

import io.github.cwireset.tcc.domain.Usuario;
import io.github.cwireset.tcc.exception.RecursoNaoEncontradoException;
import io.github.cwireset.tcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioPorCpfService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario buscar(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException(Usuario.class, cpf, "CPF"));
    }

}
