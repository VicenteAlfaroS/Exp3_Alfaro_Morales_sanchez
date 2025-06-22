package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Pedido;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

      @Autowired
    private PedidoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findByAll() {
       return (List<Pedido>) repository.findAll();
        
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pedido> findById(Long id) {
         return repository.findById(id);
       
    }

    @Override
    @Transactional
    public Pedido save(Pedido unPedido) {
        return repository.save(unPedido);
    }

    @Override
    @Transactional
    public Optional<Pedido> delete(Pedido unPedido) {
        Optional<Pedido> pedidoOPtional = repository.findById(unPedido.getId());
        pedidoOPtional.ifPresent(pedidoDB->{
            repository.delete(unPedido);
        });
        return pedidoOPtional;
    }
    }


