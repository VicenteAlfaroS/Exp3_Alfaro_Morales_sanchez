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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/ecomarket/reportes")
public class ReporteController {

  @Autowired
    private ReporteService service;

    @Operation(summary = "Muestra todos los reportes creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Obtencion de reportes exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudieron obtener los reportes")
    })
    @GetMapping
    public List<Reporte> List(){
        return service.findByAll();
    }   

    @Operation(summary = "Muestra un reporte en especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "obtencion de reporte exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "20")
            )),
        @ApiResponse(responseCode = "400",description = "Id de reporte no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Reporte> reporteOptional = service.findById(id);
        if(reporteOptional.isPresent()){
            return ResponseEntity.ok(reporteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crea un nuevo reporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte creado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudo crear el reporte")
    })
    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody Reporte unReporte){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unReporte));
    }

    @Operation(summary = "Actualiza los datos de un reporte creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "datos actualizados correctamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se encontro el reporte a modificar")
    })
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

    @Operation(summary = "Elimina un reporte especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte eliminado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "Reporte no encontrado, no se pudo eliminar")
    })
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
