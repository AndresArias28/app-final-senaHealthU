package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.AdminDTO;
import com.gym.gym_ver2.domain.model.dto.UsuarioDTO;
import com.gym.gym_ver2.domain.model.entity.Rol;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.infraestructure.repository.RolRepository;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements  AdminService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public AdminServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional
    public List<AdminDTO> getAdmins() {
        Rol rol = rolRepository.findById(2).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        List<Usuario> usuariosAdmins = usuarioRepository.findAllByRol(rol);
        return usuariosAdmins.stream()
                .map(usr -> new AdminDTO(
                        usr.getIdUsuario(),
                        usr.getNombreUsuario(),
                        usr.getEmailUsuario(),
                        usr.getIdRol().getIdRol()
                ))
                .toList();
    }

}
