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
import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Reporte;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.ReporteRepository;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.ReporteServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
public class ReporteRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean
    private ReporteServiceImpl reporteServiceImpl;

    @Mock
    private ReporteRepository reporteRepository;

    private List<Reporte> reportesLista;

    @Test
    public void verReportesTest() throws Exception{
        when(reporteServiceImpl.findByAll()).thenReturn(reportesLista);
        mockmvc.perform(get("/api/ecomarket/reportes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunReporteTest(){
        Reporte unReporte = new Reporte(1L, "Pedido nunca fue recibido", "22/06/2024", 3L);
        try{
            when(reporteServiceImpl.findById(1L)).thenReturn(Optional.of(unReporte));
            mockmvc.perform(get("/api/ecomarket/reportes/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        catch(Exception ex){
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    public void reporteNoExisteTest() throws Exception{
        when(reporteServiceImpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/ecomarket/reportes/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearReporteTest() throws Exception{
        Reporte unReporte = new Reporte(null, "Pedido nunca fue recibido", "22/06/2024", 3L);
        Reporte otroReporte = new Reporte(5L, "Pedido con productos defectuosos", "15/05/2024", 2L);
        when(reporteServiceImpl.save(any(Reporte.class))).thenReturn(otroReporte);
        mockmvc.perform(post("/api/ecomarket/reportes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unReporte)))
                .andExpect(status().isCreated());
    }

    @Test
    public void eliminarunReporteTest(){
        Reporte unReporte = new Reporte(1L, "Pedido nunca fue recibido", "22/06/2024", 3L);
        try{
            when(reporteServiceImpl.findById(1L)).thenReturn(Optional.of(unReporte));
            mockmvc.perform(delete("/api/ecomarket/reportes/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(unReporte)))
                    .andExpect(status().isNotFound());
        }
        catch(Exception ex){
            fail("El testing lanzo un error" + ex.getMessage());
        }
    }

    @Test
    void eliminarReporteNoExisteTest() {
        Reporte reporte = new Reporte();
        reporte.setId(2L);

        Mockito.when(reporteRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Reporte> resultado = reporteServiceImpl.delete(reporte);


        assertFalse(resultado.isPresent());
        Mockito.verify(reporteRepository, never()).delete(any());
    }


    @Test
    void modificarReporteExistenteTest() throws Exception {

        Reporte reporteExistente = new Reporte(1L, "Pedido nunca fue recibido", "22/06/2024", 3L);

        Reporte reporteModificado = new Reporte(1L, "Pedido con producto defectuoso", "15/03/2024", 3L);

        when(reporteServiceImpl.findById(1L)).thenReturn(Optional.of(reporteExistente));
        when(reporteServiceImpl.save(any(Reporte.class))).thenReturn(reporteModificado);

        mockmvc.perform(put("/api/ecomarket/reportes/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporteModificado)))
                .andExpect(status().isOk());

        verify(reporteServiceImpl).findById(1L);
        verify(reporteServiceImpl).save(any(Reporte.class));
    }
}
