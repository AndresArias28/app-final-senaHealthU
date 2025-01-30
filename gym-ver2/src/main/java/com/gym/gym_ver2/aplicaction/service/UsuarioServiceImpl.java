package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.domain.model.pojos.UserResponse;
import com.gym.gym_ver2.domain.model.dto.UsuarioDTO;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public List<UsuarioDTO> getUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usr -> new UsuarioDTO(
                        usr.getIdUsuario(),
                        usr.getNombreUsuario(), // Nombre
                        usr.getEmailUsuario(),
                        usr.getIdRol().getIdRol()
                ))
                .toList();
    }

    @Override
    public void createUser(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDTO getUser(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElse(null);
        assert usuario != null;
        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getEmailUsuario(),
                usuario.getIdRol().getIdRol()
        );
    }

    @Transactional
    @Override
    public UserResponse actualizarUsuario(UsuarioDTO userRequest) {
        Optional<Usuario> usuario = usuarioRepository.findById(userRequest.getIdUsuario());
        if (usuario.isEmpty()) {
            return new UserResponse("Usuario no encontrado");
        }
        // Validar campos de entrada
        if (userRequest.getNombreUsuario() == null || userRequest.getNombreUsuario().isEmpty() ||
                userRequest.getEmailUsuario() == null || userRequest.getEmailUsuario().isEmpty()) {
            return new UserResponse("Datos inválidos: nombre o email vacío");
        }
        // Actualizar el usuario
        usuarioRepository.updateUser(
                userRequest.getIdUsuario(),
                userRequest.getNombreUsuario(),
                userRequest.getEmailUsuario()
        );
        return new UserResponse("Usuario actualizado correctamente");
    }
}
