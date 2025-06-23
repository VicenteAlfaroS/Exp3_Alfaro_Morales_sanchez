package com.vicente.springboot.app.ecomarket.ecomarket_crud.restcontrollers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Pedido;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.PedidoRepository;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.PedidoServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
public class PedidoRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean
    private PedidoServiceImpl pedidoServiceImpl;

    @Mock
    private PedidoRepository pedidoRepository;

    private List<Pedido> pedidosLista;

    @Test
    public void verPedidosTest() throws Exception{
        when(pedidoServiceImpl.findByAll()).thenReturn(pedidosLista);
        mockmvc.perform(get("/api/ecomarket/pedidos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunPedidoTest(){
        Pedido unPedido = new Pedido(1L,4L,"25/04/2024","Entregado","1,3,3,6,7");
        try{
            when(pedidoServiceImpl.findById(1L)).thenReturn(Optional.of(unPedido));
            mockmvc.perform(get("/api/ecomarket/pedidos/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        catch(Exception ex){
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    public void pedidoNoExisteTest() throws Exception{
        when(pedidoServiceImpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/ecomarket/pedidos/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearPedidoTest() throws Exception{
        Pedido unPedido = new Pedido(null,4L,"25/04/2024","Entregado","1,3,3,6,7");
        Pedido otroPedido = new Pedido(5L,2L,"12/02/2024","Entregado","1,2,5");
        when(pedidoServiceImpl.save(any(Pedido.class))).thenReturn(otroPedido);
        mockmvc.perform(post("/api/ecomarket/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unPedido)))
                .andExpect(status().isCreated());
    }

    @Test
    public void eliminarunPedidoTest(){
        Pedido unPedido = new Pedido(1L,4L,"25/04/2024","Entregado","1,3,3,6,7");
        try{
            when(pedidoServiceImpl.findById(1L)).thenReturn(Optional.of(unPedido));
            mockmvc.perform(delete("/api/ecomarket/pedidos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(unPedido)))
                    .andExpect(status().isNotFound());
        }
        catch(Exception ex){
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    void eliminarPedidoNoExisteTest() {
        Pedido pedido = new Pedido();
        pedido.setId(2L);

        Mockito.when(pedidoRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Pedido> resultado = pedidoServiceImpl.delete(pedido);


        assertFalse(resultado.isPresent());
        Mockito.verify(pedidoRepository, never()).delete(any());
    }


    @Test
    void modificarPedidoExistenteTest() throws Exception {

        Pedido pedidoExistente = new Pedido(1L,4L,"25/04/2024","Entregado","1,3,3,6,7");

        Pedido pedidoModificado = new Pedido(1L,2L,"12/02/2024","Entregado","1,2,5");

        when(pedidoServiceImpl.findById(1L)).thenReturn(Optional.of(pedidoExistente));
        when(pedidoServiceImpl.save(any(Pedido.class))).thenReturn(pedidoModificado);

        mockmvc.perform(put("/api/ecomarket/pedidos/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoModificado)))
                .andExpect(status().isOk());

        verify(pedidoServiceImpl).findById(1L);
        verify(pedidoServiceImpl).save(any(Pedido.class));
    }

}
