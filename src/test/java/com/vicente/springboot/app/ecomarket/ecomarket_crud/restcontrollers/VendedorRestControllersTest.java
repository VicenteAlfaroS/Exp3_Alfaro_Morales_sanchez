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
import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Vendedor;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.VendedorRepository;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.VendedorServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class VendedorRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VendedorServiceImpl vendedorServiceImpl;

    @Mock
    private VendedorRepository vendedorRepository;

    private List<Vendedor> vendedoresLista;

    @Test
    public void verVendedoresTest() throws Exception {
        when(vendedorServiceImpl.findByAll()).thenReturn(vendedoresLista);
        mockmvc.perform(get("/api/ecomarket/vendedores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunVendedorTest() {
        Vendedor unVendedor = new Vendedor(1L, "12.546.344-6", "Humberto Suazo", "chupete2006");
        try {
            when(vendedorServiceImpl.findById(1L)).thenReturn(Optional.of(unVendedor));
            mockmvc.perform(get("/api/ecomarket/vendedores/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    public void vendedorNoExisteTest() throws Exception {
        when(vendedorServiceImpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/ecomarket/vendedors/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearvendedorTest() throws Exception {
        Vendedor unVendedor = new Vendedor(null, "12.546.344-6", "Humberto Suazo", "chupete2010");
        Vendedor otroVendedor = new Vendedor(5L, "15.223.765-8", "Matias Fernandez", "matigol2006");
        when(vendedorServiceImpl.save(any(Vendedor.class))).thenReturn(otroVendedor);
        mockmvc.perform(post("/api/ecomarket/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unVendedor)))
                .andExpect(status().isCreated());
    }

    @Test
    public void eliminarunvendedorTest() {
        Vendedor unVendedor = new Vendedor(1L, "12.546.344-6", "Humberto Suazo", "chupete2006");
        try {
            when(vendedorServiceImpl.findById(1L)).thenReturn(Optional.of(unVendedor));
            mockmvc.perform(delete("/api/ecomarket/vendedores/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(unVendedor)))
                    .andExpect(status().isNotFound());
        } catch (Exception ex) {
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    void eliminarvendedorNoExisteTest() {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(2L);

        Mockito.when(vendedorRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Vendedor> resultado = vendedorServiceImpl.delete(vendedor);

        assertFalse(resultado.isPresent());
        Mockito.verify(vendedorRepository, never()).delete(any());
    }

    @Test
    void modificarvendedorExistenteTest() throws Exception {

        Vendedor vendedorExistente = new Vendedor(1L, "12.546.344-6", "Humberto Suazo", "chupete2006");

        Vendedor vendedorModificado = new Vendedor(1L, "15.223.765-8", "Matias Fernandez", "matigol2006");

        when(vendedorServiceImpl.findById(1L)).thenReturn(Optional.of(vendedorExistente));
        when(vendedorServiceImpl.save(any(Vendedor.class))).thenReturn(vendedorModificado);

        mockmvc.perform(put("/api/ecomarket/vendedores/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendedorModificado)))
                .andExpect(status().isOk());

        verify(vendedorServiceImpl).findById(1L);
        verify(vendedorServiceImpl).save(any(Vendedor.class));
    }
}
