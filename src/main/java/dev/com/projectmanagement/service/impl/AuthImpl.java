package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.dto.UserDTO;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.register.RegisterRequest;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFullName(registerRequest.getFullName());
        user.setJobPosition(registerRequest.getJobPosition());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setBirthday(registerRequest.getBirthday());
        user.setAddress(registerRequest.getAddress());

        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(createdUser.getEmail());
        userDTO.setUsername(createdUser.getUsername());
        userDTO.setFullName(createdUser.getFullName());
        userDTO.setJobPosition(createdUser.getJobPosition());
        userDTO.setPhoneNumber(createdUser.getPhoneNumber());
        userDTO.setBirthday(createdUser.getBirthday());
        userDTO.setAddress(createdUser.getAddress());


        return userDTO;
    }
}
