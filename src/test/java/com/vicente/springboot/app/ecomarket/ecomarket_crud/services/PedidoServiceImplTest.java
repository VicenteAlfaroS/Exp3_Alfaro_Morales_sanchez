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

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Pedido;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.PedidoRepository;

public class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock
    private PedidoRepository repository;

    List<Pedido> list = new ArrayList<Pedido>();

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.chargePedido();
    }

    @Test
    public void findByAllTest(){

        when(repository.findAll()).thenReturn(list);

        List<Pedido> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository,times(1)).findAll();
    }

    public void chargePedido(){
        Pedido ped1 = new Pedido(1L,3L,"23/05/2024","Entregado","1,5,3,2,2");
        Pedido ped2 = new Pedido(2L,6L,"15/06/2024","En transito","1,6,6,7");
        Pedido ped3 = new Pedido(3L,5L,"23/06/2024","En preparacion","1,4");
        
        list.add(ped1);
        list.add(ped2);
        list.add(ped3);
    }
}
