package com.sistema.cloudinary.application.ports.input;


import com.sistema.cloudinary.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface UsuarioService {

    List<Usuario> listaUsuarios();
    Usuario getUsuarioId(Long usuarioId);
    Usuario createUser(Usuario usuario);
    Usuario updateUser(Long usuarioId,Usuario usuario);
    Usuario updateFoto(Long usuarioId, MultipartFile multipartFile);
}
