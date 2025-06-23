package com.vicente.springboot.app.ecomarket.ecomarket_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Pedido;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.PedidoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("api/ecomarket/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Operation(summary = "Muestra todos los pedidos creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Obtencion de pedidos exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudieron obtener los pedidos")
    })
    @GetMapping
    public List<Pedido> List(){
        return service.findByAll();
    }


    @Operation(summary = "Muestra un pedido en especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "obtencion de pedido exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "20")
            )),
        @ApiResponse(responseCode = "400",description = "Id de pedido no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Pedido> pedidoOptional = service.findById(id);
        if(pedidoOptional.isPresent()){
            return ResponseEntity.ok(pedidoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Crea un nuevo pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudo crear el pedido")
    })
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido unPedido){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unPedido));
    }

    @Operation(summary = "Actualiza los datos de un pedido creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "datos actualizados correctamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se encontro el pedido a modificar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Pedido unPedido){
        Optional <Pedido> pedidoOptional = service.findById(id);
        if(pedidoOptional.isPresent()){
            Pedido pedidoExistente = pedidoOptional.get();
            pedidoExistente.setEstado(unPedido.getEstado());
            pedidoExistente.setFecha(unPedido.getFecha());
            pedidoExistente.setProductosId(unPedido.getProductosId());
            pedidoExistente.setUserId(unPedido.getUserId());
            Pedido pedidoModificado = service.save(pedidoExistente);
            return ResponseEntity.ok(pedidoModificado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Elimina un pedido especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido eliminado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "Pedido no encontrado, no se pudo eliminar")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Pedido unPedido = new Pedido();
        unPedido.setId(id);
        Optional<Pedido> pedidoOptional = service.delete(unPedido);
        if(pedidoOptional.isPresent()){
            return ResponseEntity.ok(pedidoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

}
