package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.dto.UserDTO;
import dev.com.projectmanagement.model.login.LoginRequest;
import dev.com.projectmanagement.model.register.RegisterRequest;
import dev.com.projectmanagement.model.request.MailRequest;
import dev.com.projectmanagement.model.response.AuthenticationResponse;
import dev.com.projectmanagement.service.AuthService;
import dev.com.projectmanagement.service.jwt.UserDetailsServiceImpl;
import dev.com.projectmanagement.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request){
        UserDTO createUser = authService.createUser(request);
        if(createUser == null){
            return new ResponseEntity<>("User created fail, try again later!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public AuthenticationResponse createAuthenticationToken(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
            throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password.");
        } catch (DisabledException disabledException){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created. Register and try again!");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());
        return new AuthenticationResponse(jwt, userDetails.getUsername());
    }

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestBody MailRequest email){
        boolean emailExist = authService.checkEmail(email);
        return ResponseEntity.ok(emailExist);
    }
}
