package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Producto;

public interface ProductoService {

    List<Producto> findByAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto unProducto);

    Optional<Producto> delete(Producto unProducto);
    
}
