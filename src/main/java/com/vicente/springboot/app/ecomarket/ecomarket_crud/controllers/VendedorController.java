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

    @GetMapping
    public List<Vendedor> List(){
        return service.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Vendedor> vendedorOptional = service.findById(id);
        if(vendedorOptional.isPresent()){
            return ResponseEntity.ok(vendedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Vendedor> crear(@RequestBody Vendedor unVendedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unVendedor));
    }    

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
