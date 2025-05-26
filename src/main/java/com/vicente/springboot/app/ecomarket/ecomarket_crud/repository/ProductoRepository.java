package com.vicente.springboot.app.ecomarket.ecomarket_crud.repository;

import org.springframework.data.repository.CrudRepository;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Producto;

public interface ProductoRepository extends CrudRepository<Producto,Long> {

}
