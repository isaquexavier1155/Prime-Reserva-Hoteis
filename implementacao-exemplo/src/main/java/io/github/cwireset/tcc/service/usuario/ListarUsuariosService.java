package io.github.cwireset.tcc.service.usuario;

import io.github.cwireset.tcc.domain.Usuario;
import io.github.cwireset.tcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarUsuariosService {

    @Autowired
    private UsuarioRepository repository;

    public Page<Usuario> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
