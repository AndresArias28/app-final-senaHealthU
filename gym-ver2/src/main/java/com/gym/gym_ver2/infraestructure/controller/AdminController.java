package com.gym.gym_ver2.infraestructure.controller;

import com.gym.gym_ver2.aplicaction.service.AdminService;
import com.gym.gym_ver2.domain.model.dto.AdminDTO;
import com.gym.gym_ver2.domain.model.requestModels.RegisterAdminRequest;
import com.gym.gym_ver2.infraestructure.auth.AuthResponse;
import com.gym.gym_ver2.infraestructure.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin Controller", description = "Endpoints para gestion de admins")

@RequestMapping("/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/obtenerAdmins")
    public ResponseEntity<List<AdminDTO>> obtenerAdmins() {
        try {
            List<AdminDTO> admins = adminService.getAdmins();
            return ResponseEntity.ok(admins);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterAdminRequest rq) {
        try{
            if (rq == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(adminService.registerAdmin(rq));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
