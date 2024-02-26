package com.sistema.cloudinary.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long usuarioId;
    String nombre;
    String apellido;
    String fotoDePerfil;

    @PrePersist
    void colocandoDatos(){
        if (fotoDePerfil==null){
            fotoDePerfil="sin_imagen";
        }
    }
}
