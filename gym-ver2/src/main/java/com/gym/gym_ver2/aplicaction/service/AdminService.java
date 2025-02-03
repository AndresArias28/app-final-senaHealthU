package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.AdminDTO;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.domain.model.requestModels.RegisterAdminRequest;
import com.gym.gym_ver2.infraestructure.auth.AuthResponse;
import com.gym.gym_ver2.infraestructure.auth.RegisterRequest;

import java.util.List;

public interface  AdminService {

    List<AdminDTO> getAdmins();

    AuthResponse registerAdmin(RegisterAdminRequest rq);
}
