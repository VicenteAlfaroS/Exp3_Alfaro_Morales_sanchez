package com.vicente.springboot.app.ecomarket.ecomarket_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Vendedor;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.VendedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("api/ecomarket/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService service;

    @Operation(summary = "Muestra todos los vendedores creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Obtencion de vendedores exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudieron obtener los vendedores")
    })
    @GetMapping
    public List<Vendedor> List(){
        return service.findByAll();
    }

    @Operation(summary = "Muestra un vendedor en especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "obtencion de vendedor exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "20")
            )),
        @ApiResponse(responseCode = "400",description = "Id de vendedor no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Vendedor> vendedorOptional = service.findById(id);
        if(vendedorOptional.isPresent()){
            return ResponseEntity.ok(vendedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Crea un nuevo vendedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vendedor creado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudo crear el vendedor")
    })
    @PostMapping
    public ResponseEntity<Vendedor> crear(@RequestBody Vendedor unVendedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unVendedor));
    }    

    @Operation(summary = "Actualiza los datos de un vendedor creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "datos actualizados correctamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se encontro el vendedor a modificar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Vendedor unVendedor){
        Optional<Vendedor> vendedorOptional = service.findById(id);
        if(vendedorOptional.isPresent()){
            Vendedor vendedorExistente = vendedorOptional.get();
            vendedorExistente.setNombre(unVendedor.getNombre());
            vendedorExistente.setContrasenia(unVendedor.getContrasenia());
            vendedorExistente.setRut(unVendedor.getRut());
            Vendedor vendedorModificado = service.save(vendedorExistente);
            return ResponseEntity.ok(vendedorModificado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Elimina un vendedor especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vendedor eliminado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "Vendedor no encontrado, no se pudo eliminar")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Vendedor unVendedor = new Vendedor();
        unVendedor.setId(id);
        Optional<Vendedor> vendedorOptional = service.delete(unVendedor);
        if(vendedorOptional.isPresent()){
            return ResponseEntity.ok(vendedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
}
