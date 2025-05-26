package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Usuario;

public interface UsuarioService {

    List<Usuario> findByAll(); 

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario unUsuario);

    Optional<Usuario> delete(Usuario unUsuario);
    
}
