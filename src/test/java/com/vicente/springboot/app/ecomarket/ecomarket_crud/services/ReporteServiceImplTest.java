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

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Reporte;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.ReporteRepository;

public class ReporteServiceImplTest {

    @InjectMocks
    private ReporteServiceImpl service;

    @Mock
    private ReporteRepository repository;

    List<Reporte> list = new ArrayList<Reporte>();

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.chargeReportes();
    }

    @Test
    public void findByAllTest(){

        when(repository.findAll()).thenReturn(list);

        List<Reporte> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository,times(1)).findAll();
    }

    public void chargeReportes(){
        Reporte repo1 = new Reporte(1L,"Pedido no recibido","23/05/2024",4L);
        Reporte repo2 = new Reporte(2L,"Faltaron productos del pedido","15/06/2024",5L);
        Reporte repo3 = new Reporte(3L,"Recibi producto defectuoso","23/06/2024",6L);
        
        list.add(repo1);
        list.add(repo2);
        list.add(repo3);
    }
}
