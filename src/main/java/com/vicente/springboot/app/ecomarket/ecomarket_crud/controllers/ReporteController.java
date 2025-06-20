package com.vicente.springboot.app.ecomarket.ecomarket_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Reporte;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.ReporteService;

@RestController
@RequestMapping("api/ecomarket/reportes")
public class ReporteController {

  @Autowired
    private ReporteService service;

    @GetMapping
    public List<Reporte> List(){
        return service.findByAll();
    }   

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Reporte> reporteOptional = service.findById(id);
        if(reporteOptional.isPresent()){
            return ResponseEntity.ok(reporteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody Reporte unReporte){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unReporte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Reporte unReporte){
        Optional <Reporte> reporteOptional = service.findById(id);
        if(reporteOptional.isPresent()){
            Reporte reporteExistente = reporteOptional.get();
            reporteExistente.setDescripcion(unReporte.getDescripcion());
            reporteExistente.setFecha(unReporte.getFecha());
            reporteExistente.setIdUsuario(unReporte.getIdUsuario());
            Reporte reporteModificado = service.save(reporteExistente);
            return ResponseEntity.ok(reporteModificado);
        }
        return ResponseEntity.notFound().build();
    }

        @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Reporte unReporte = new Reporte();
        unReporte.setId(id);
        Optional<Reporte> reporteOptional = service.delete(unReporte);
        if(reporteOptional.isPresent()){
            return ResponseEntity.ok(reporteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

}
