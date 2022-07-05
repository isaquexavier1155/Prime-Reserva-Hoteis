package io.github.cwireset.tcc.service.usuario;

import io.github.cwireset.tcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExisteUsuarioMesmoEmailService {

    @Autowired
    private UsuarioRepository repository;

    public boolean existe(String email) {
        return repository.existsByEmail(email);
    }

    public boolean existeComIdDiferente(String email, Long id) {
        return repository.existsByEmailAndIdNot(email, id);
    }

}
