package com.vicente.springboot.app.ecomarket.ecomarket_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Usuario;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.services.UsuarioService;

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
@RequestMapping("api/ecomarket/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Operation(summary = "Muestra todos los usuarios creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Obtencion de usuarios exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudieron obtener los usuarios")
    })
    @GetMapping
    public List<Usuario> List(){
        return service.findByAll();
    }

    @Operation(summary = "Muestra un usuario en especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "obtencion de usuario exitosa",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "20")
            )),
        @ApiResponse(responseCode = "400",description = "Id de usuario no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.findById(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crea un nuevo usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se pudo crear el usuario")
    })
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario unUsuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unUsuario));
    }
    
    
    @Operation(summary = "Actualiza los datos de un usuario creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "datos actualizados correctamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "No se encontro el usuario a modificar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Usuario unUsuario){
        Optional<Usuario> usuarioOptional = service.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNombre(unUsuario.getNombre());
            usuarioExistente.setDireccion(unUsuario.getDireccion());
            usuarioExistente.setContrasenia(unUsuario.getContrasenia());
            usuarioExistente.setRut(unUsuario.getRut());
            Usuario usuarioModificado = service.save(usuarioExistente);
            return ResponseEntity.ok(usuarioModificado);
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Elimina un usuario especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente",
            content = @Content(mediaType = "text/plain",
                examples = @ExampleObject(value = "")
            )),
        @ApiResponse(responseCode = "400", description = "Usuario no encontrado, no se pudo eliminar")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Usuario unUsuario = new Usuario();
        unUsuario.setId(id);
        Optional<Usuario> usuarioOptional = service.delete(unUsuario);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

}
