package io.github.cwireset.tcc.service.usuario;

import io.github.cwireset.tcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExisteUsuarioMesmoCpfService {

    @Autowired
    private UsuarioRepository repository;

    public boolean existe(String cpf) {
        return repository.existsByCpf(cpf);
    }

}
