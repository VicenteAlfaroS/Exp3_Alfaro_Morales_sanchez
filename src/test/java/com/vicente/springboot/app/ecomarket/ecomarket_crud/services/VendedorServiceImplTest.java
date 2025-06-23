package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Vendedor;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.VendedorRepository;

public class VendedorServiceImplTest {

    @InjectMocks
    private VendedorServiceImpl service;

    @Mock
    private VendedorRepository repository;

    List<Vendedor> list = new ArrayList<Vendedor>();

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.chargeVendedor();
    }

    @Test
    public void findByAllTest(){

        when(repository.findAll()).thenReturn(list);

        List<Vendedor> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository,times(1)).findAll();
    }

    public void chargeVendedor(){
        Vendedor vend1 = new Vendedor(Long.valueOf(1),"21.443.332-5","Jose Perez","PerrosyGatos12");
        Vendedor vend2 = new Vendedor(Long.valueOf(2),"18.334.565-9","Lucas Ruiz","Temporadadepatos123");
        Vendedor vend3 = new Vendedor(Long.valueOf(3),"12.542.664-4","Humberto Suazo","Chupete2006");
        
        list.add(vend1);
        list.add(vend2);
        list.add(vend3);
    }
}
