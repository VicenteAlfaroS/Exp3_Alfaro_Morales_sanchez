package com.vicente.springboot.app.ecomarket.ecomarket_crud.controllers;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Producto;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("api/ecomarket/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Operation(summary = "Muestra todos los productos creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Obtencion de productos exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudieron obtener los productos")
    })
    @GetMapping
    public List<Producto> List(){
        return service.findByAll();
    }
 
    @Operation(summary = "Muestra un producto en especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "obtencion de producto exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "20")
            )),
        @ApiResponse(responseCode = "400",description = "Id de producto no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Producto> productoOptional = service.findById(id);
        if(productoOptional.isPresent()){
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crea un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudo crear el producto")
    })
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto unProducto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unProducto));
    }

    @Operation(summary = "Actualiza los datos de un producto creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "datos actualizados correctamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se encontro el producto a modificar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Producto unProducto){
        Optional <Producto> productoOptional = service.findById(id);
        if(productoOptional.isPresent()){
            Producto productoExistente = productoOptional.get();
            productoExistente.setNombre(unProducto.getNombre());
            productoExistente.setDescripcion(unProducto.getDescripcion());
            productoExistente.setPrecio(unProducto.getPrecio());
            productoExistente.setStock(unProducto.getStock());
            Producto productoModificado = service.save(productoExistente);
            return ResponseEntity.ok(productoModificado);
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Elimina un producto especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "Producto no encontrado, no se pudo eliminar")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Producto unProducto = new Producto();
        unProducto.setId(id);
        Optional<Producto> productoOptional = service.delete(unProducto);
        if(productoOptional.isPresent()){
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    
    
}
