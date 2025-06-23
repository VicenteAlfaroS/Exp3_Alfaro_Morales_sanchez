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
import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Usuario;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.UsuarioRepository;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.UsuarioServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioServiceImpl usuarioServiceImpl;

    @Mock
    private UsuarioRepository usuarioRepository;

    private List<Usuario> usuariosLista;

    @Test
    public void verUsuariosTest() throws Exception {
        when(usuarioServiceImpl.findByAll()).thenReturn(usuariosLista);
        mockmvc.perform(get("/api/ecomarket/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunUsuarioTest() {
        Usuario unUsuario = new Usuario(1L, "12.546.344-6", "Humberto Suazo", "planeta gol 1243", "chupete2006");
        try {
            when(usuarioServiceImpl.findById(1L)).thenReturn(Optional.of(unUsuario));
            mockmvc.perform(get("/api/ecomarket/usuarios/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    public void usuarioNoExisteTest() throws Exception {
        when(usuarioServiceImpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/ecomarket/usuarios/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearUsuarioTest() throws Exception {
        Usuario unUsuario = new Usuario(null, "12.546.344-6", "Humberto Suazo", "planeta gol 1243", "chupete2010");
        Usuario otroUsuario = new Usuario(5L, "15.223.765-8", "Matias Fernandez", "avenida crack 3445", "matigol2006");
        when(usuarioServiceImpl.save(any(Usuario.class))).thenReturn(otroUsuario);
        mockmvc.perform(post("/api/ecomarket/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unUsuario)))
                .andExpect(status().isCreated());
    }

    @Test
    public void eliminarunUsuarioTest() {
        Usuario unUsuario = new Usuario(1L, "12.546.344-6", "Humberto Suazo", "planeta gol 1243", "chupete2006");
        try {
            when(usuarioServiceImpl.findById(1L)).thenReturn(Optional.of(unUsuario));
            mockmvc.perform(delete("/api/ecomarket/usuarios/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(unUsuario)))
                    .andExpect(status().isNotFound());
        } catch (Exception ex) {
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    void eliminarUsuarioNoExisteTest() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);

        Mockito.when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioServiceImpl.delete(usuario);

        assertFalse(resultado.isPresent());
        Mockito.verify(usuarioRepository, never()).delete(any());
    }

    @Test
    void modificarUsuarioExistenteTest() throws Exception {

        Usuario usuarioExistente = new Usuario(1L, "12.546.344-6", "Humberto Suazo", "planeta gol 1243", "chupete2006");

        Usuario usuarioModificado = new Usuario(1L, "15.223.765-8", "Matias Fernandez", "avenida crack 3445", "matigol2006");

        when(usuarioServiceImpl.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioServiceImpl.save(any(Usuario.class))).thenReturn(usuarioModificado);

        mockmvc.perform(put("/api/ecomarket/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioModificado)))
                .andExpect(status().isOk());

        verify(usuarioServiceImpl).findById(1L);
        verify(usuarioServiceImpl).save(any(Usuario.class));
    }
}
