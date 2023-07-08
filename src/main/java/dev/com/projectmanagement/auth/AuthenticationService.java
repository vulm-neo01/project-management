package dev.com.projectmanagement.auth;

import dev.com.projectmanagement.config.JwtService;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.login.Login;
import dev.com.projectmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;
    public AuthenticationResponse register(User request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .jobPosition(request.getJobPosition())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .address(request.getAddress())
                .build();
        repository.insert(user);

//        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
//                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(Login request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail()).orElseThrow();

//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
        return null;
    }
}
