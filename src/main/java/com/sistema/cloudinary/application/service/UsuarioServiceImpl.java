package com.sistema.cloudinary.application.service;

import com.sistema.cloudinary.application.ports.input.UsuarioService;
import com.sistema.cloudinary.application.ports.output.UsuarioRepository;
import com.sistema.cloudinary.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CloudinaryService cloudinaryService;
    @Override
    public List<Usuario> listaUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new RuntimeException("usuario no encontrado"));
    }

    @Override
    public Usuario createUser(Usuario usuario) {
        return  usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUser(Long usuarioId, Usuario usuario) {

        return null;
    }

    @Override
    public Usuario updateFoto(Long usuarioId, MultipartFile multipartFile) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new RuntimeException("usuario no encontrado"));
        usuario.setUsuarioId(usuarioId);
        String idPublic= cloudinaryService.extractPublicIdFromImageUrl(usuario.getFotoDePerfil());
        try {
            cloudinaryService.delete(idPublic);
        } catch (IOException e) {
            throw new RuntimeException("no se pudo eliminar el antiguo foto");
        }
        usuario.setFotoDePerfil(cloudinaryService.uploadFile(multipartFile));

        return usuarioRepository.save(usuario);
    }
}
