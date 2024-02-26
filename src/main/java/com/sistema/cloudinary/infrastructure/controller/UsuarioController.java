package com.sistema.cloudinary.infrastructure.controller;

import com.sistema.cloudinary.application.ports.input.UsuarioService;
import com.sistema.cloudinary.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/{usuarioId}")
    ResponseEntity<?> updateFoto(@PathVariable Long usuarioId, MultipartFile multipartFile){
       return  ResponseEntity.ok(usuarioService.updateFoto(usuarioId,multipartFile));
    }
    @GetMapping
    ResponseEntity<?> listUsuario(){
        return  ResponseEntity.ok(usuarioService.listaUsuarios());
    }
    @PostMapping("/create")
    ResponseEntity<?> createUser(@RequestBody Usuario usuario){
        return  ResponseEntity.ok(usuarioService.createUser(usuario));
    }
    @PutMapping("/edit/{usuarioId}")
    ResponseEntity<?> updateUser(@PathVariable Long usuarioId ,@RequestParam  MultipartFile multipartFile){
        return  ResponseEntity.ok(usuarioService.updateFoto(usuarioId,multipartFile));
    }

}
