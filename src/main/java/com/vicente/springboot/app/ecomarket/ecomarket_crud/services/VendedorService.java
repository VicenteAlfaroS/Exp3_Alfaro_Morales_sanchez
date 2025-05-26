package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Vendedor;

public interface VendedorService {

    List<Vendedor> findByAll();

    Optional<Vendedor> findById(Long id);

    Vendedor save(Vendedor unVendedor);

    Optional<Vendedor> delete(Vendedor unVendedor);
}
