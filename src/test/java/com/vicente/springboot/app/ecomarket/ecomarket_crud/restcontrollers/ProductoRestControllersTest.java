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
import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Producto;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.ProductoRepository;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.ProductoServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductoRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean
    private ProductoServiceImpl productoServiceImpl;

    @Mock
    private ProductoRepository productoRepository;

    private List<Producto> productosLista;

    @Test
    public void verProductosTest() throws Exception{
        when(productoServiceImpl.findByAll()).thenReturn(productosLista);
        mockmvc.perform(get("/api/ecomarket/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunProductoTest(){
        Producto unProducto = new Producto(1L,"Galletas","de avena",1100,15);
        try{
            when(productoServiceImpl.findById(1L)).thenReturn(Optional.of(unProducto));
            mockmvc.perform(get("/api/ecomarket/productos/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        catch(Exception ex){
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    public void productoNoExisteTest() throws Exception{
        when(productoServiceImpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/ecomarket/productos/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearProductoTest() throws Exception{
        Producto unProducto = new Producto(null,"pan amasado", "producto chileno", 300,10);
        Producto otroProducto = new Producto(4L, "pan amasado integral", "producto chileno", 350,8);
        when(productoServiceImpl.save(any(Producto.class))).thenReturn(otroProducto);
        mockmvc.perform(post("/api/ecomarket/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unProducto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void eliminarunProductoTest(){
        Producto unProducto = new Producto(1L,"pan amasado", "producto chileno", 300,10);
        try{
            when(productoServiceImpl.findById(1L)).thenReturn(Optional.of(unProducto));
            mockmvc.perform(delete("/api/ecomarket/productos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(unProducto)))
                    .andExpect(status().isNotFound());
        }
        catch(Exception ex){
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    void eliminarProductoNoExisteTest() {
        Producto producto = new Producto();
        producto.setId(2L);

        Mockito.when(productoRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Producto> resultado = productoServiceImpl.delete(producto);


        assertFalse(resultado.isPresent());
        Mockito.verify(productoRepository, never()).delete(any());
    }


    @Test
    void modificarProductoExistenteTest() throws Exception {

        // Producto original existente
        Producto productoExistente = new Producto(1L, "Platano", "Fruta amarilla", 500, 20);

        // Producto modificado que se recibe en el body
        Producto productoModificado = new Producto(1L,"Manzana Verde","Fruta verde ácida",1200,30);

        // Configurar mocks
        when(productoServiceImpl.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(productoServiceImpl.save(any(Producto.class))).thenReturn(productoModificado);

        mockmvc.perform(put("/api/ecomarket/productos/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoModificado)))
                .andExpect(status().isOk());

        verify(productoServiceImpl).findById(1L);
        verify(productoServiceImpl).save(any(Producto.class));
    }
}
