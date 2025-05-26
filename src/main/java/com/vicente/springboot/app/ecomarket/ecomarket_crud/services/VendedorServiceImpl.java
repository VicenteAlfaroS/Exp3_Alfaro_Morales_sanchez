package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Vendedor;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.VendedorRepository;

@Service
public class VendedorServiceImpl implements VendedorService {

    @Autowired
    private VendedorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Vendedor> findByAll() {
        return (List<Vendedor>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vendedor> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Vendedor save(Vendedor unVendedor) {
        return repository.save(unVendedor);
    }

    @Override
    @Transactional
    public Optional<Vendedor> delete(Vendedor unVendedor) {
        Optional<Vendedor> vendedorOptional = repository.findById(unVendedor.getId());
        vendedorOptional.ifPresent(vendedorDB->{
            repository.delete(unVendedor);
        });
        return vendedorOptional;
    }
}
