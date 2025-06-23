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

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Usuario;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.UsuarioRepository;

public class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl service;

    @Mock
    private UsuarioRepository repository;

    List<Usuario> list = new ArrayList<Usuario>();

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.chargeUsuario();
    }

    @Test
    public void findByAllTest(){

        when(repository.findAll()).thenReturn(list);

        List<Usuario> response = service.findByAll();

        assertEquals(3, response.size());
        verify(repository,times(1)).findAll();
    }

    public void chargeUsuario(){
        Usuario user1 = new Usuario(Long.valueOf(1),"21.443.332-5","Jose Perez","avenida hola 123","PerrosyGatos12");
        Usuario user2 = new Usuario(Long.valueOf(2),"18.334.565-9","Lucas Ruiz","Pajaritos 2354","Temporadadepatos123");
        Usuario user3 = new Usuario(Long.valueOf(3),"12.542.664-4","Humberto Suazo","planeta gol 4565","Chupete2006");

        list.add(user1);
        list.add(user2);
        list.add(user3);
    }
}
