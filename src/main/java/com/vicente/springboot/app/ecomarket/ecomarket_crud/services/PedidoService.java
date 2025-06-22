package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Pedido;

public interface PedidoService {

    List <Pedido> findByAll();

    Optional <Pedido> findById(Long id);

    Pedido save(Pedido unPedido);

    Optional <Pedido> delete(Pedido unPedido);
    

}
