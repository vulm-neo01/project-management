package dev.com.projectmanagement.service;

import dev.com.projectmanagement.dto.UserDTO;
import dev.com.projectmanagement.model.register.RegisterRequest;
import dev.com.projectmanagement.model.request.MailRequest;

public interface AuthService {
    UserDTO createUser(RegisterRequest registerRequest);

    boolean checkEmail(MailRequest email);
}
