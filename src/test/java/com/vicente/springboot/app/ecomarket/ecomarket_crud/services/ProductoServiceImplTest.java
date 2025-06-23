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

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Producto;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.ProductoRepository;

public class ProductoServiceImplTest {

    @InjectMocks
    private ProductoServiceImpl service;

    @Mock
    private ProductoRepository repository;

    List<Producto> list = new ArrayList<Producto>();

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.chargeProducto();
    }

    @Test
    public void findByAllTest(){

        when(repository.findAll()).thenReturn(list);

        List<Producto> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository,times(1)).findAll();
    }

    public void chargeProducto(){
        Producto prod1 = new Producto(Long.valueOf(1),"Galletas","de avena",1100,15);
        Producto prod2 = new Producto(Long.valueOf(2),"Donas","rellenas con chocolate",900,10);
        Producto prod3 = new Producto(Long.valueOf(3),"Queque","de zanahoria",4500,8);

        list.add(prod1);
        list.add(prod2);
        list.add(prod3);
    }

}
