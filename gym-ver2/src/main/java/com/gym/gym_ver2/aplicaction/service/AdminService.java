package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.AdminDTO;
import com.gym.gym_ver2.domain.model.entity.Usuario;

import java.util.List;

public interface  AdminService {

    List<AdminDTO> getAdmins();
}
