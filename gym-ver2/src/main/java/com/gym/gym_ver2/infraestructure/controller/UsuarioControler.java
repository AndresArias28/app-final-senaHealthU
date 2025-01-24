package com.gym.gym_ver2.infraestructure.controller;

import com.gym.gym_ver2.aplicaction.service.UsuarioService;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.domain.model.pojos.UserResponse;
import com.gym.gym_ver2.domain.model.pojos.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/user")
@RestController
public class UsuarioControler {

    private final UsuarioService userService;

    @Autowired
    public UsuarioControler(UsuarioService userService) {
        this.userService = userService;
    }

    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() {
        List<UsuarioDTO> usuarios = userService.getUsers();
        return ResponseEntity.ok(usuarios);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/obtenereUsario/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable("id") Integer idUsuario) {
        try{
            UsuarioDTO usuario = userService.getUser(idUsuario);
            if (usuario == null) {
                return ResponseEntity.notFound().build(); // 404 Not Found si no existe el usuario
            }
            return ResponseEntity.ok(usuario);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/actualizarUsuario")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UsuarioDTO userRequest){//metodo para actualizar Usuario
        try{ // 200 OK si se actualiza correctamente
            UserResponse res = userService.actualizarUsuario(userRequest); //envia al servicio la peticion
            if (userRequest == null) {//valida si la peticion es nula
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(res);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
