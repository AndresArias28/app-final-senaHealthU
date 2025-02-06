package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.AdminDTO;
import com.gym.gym_ver2.domain.model.entity.Rol;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.domain.model.requestModels.RegisterAdminRequest;
import com.gym.gym_ver2.infraestructure.auth.AuthResponse;
import com.gym.gym_ver2.infraestructure.repository.RolRepository;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl implements  AdminService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public AuthResponse registerAdmin(RegisterAdminRequest rq) {
        System.out.println("Accediendo al método protegido.");

        Rol rol = rolRepository.findByNombreRol("Administrador")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        Usuario usuario = Usuario.builder()
                .nombreUsuario(rq.getNombreAdmin())
                .apellidoUsuario(rq.getApellidoAdmin())
                .emailUsuario(rq.getEmailAdmin())
                .cedulaUsuario(rq.getCedulaAdmin())
                .contrasenaUsuario(passwordEncoder.encode(rq.getContrasenaAdmin()))
                .idRol(Rol.builder().idRol(2).build())
                .build();

        usuarioRepository.save(usuario);
        return AuthResponse.builder().token("Administrador registrado").build();
    }

}
