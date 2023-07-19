package dev.com.projectmanagement.service;

import dev.com.projectmanagement.dto.UserDTO;
import dev.com.projectmanagement.model.register.RegisterRequest;

public interface AuthService {
    UserDTO createUser(RegisterRequest registerRequest);
}
