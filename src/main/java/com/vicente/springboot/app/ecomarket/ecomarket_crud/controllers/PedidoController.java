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


@RestController
@RequestMapping("api/ecomarket/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

     @GetMapping
    public List<Pedido> List(){
        return service.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Pedido> pedidoOptional = service.findById(id);
        if(pedidoOptional.isPresent()){
            return ResponseEntity.ok(pedidoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido unPedido){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unPedido));
    }

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
