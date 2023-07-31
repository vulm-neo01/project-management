package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.login.LoginRequest;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.login.LoginMessage;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.UserService;
import dev.com.projectmanagement.service.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> userToChange = userRepository.findByEmail(email);
        User changingUser = userToChange.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        changingUser.setFullName(user.getFullName());
        changingUser.setJobPosition(user.getJobPosition());
        changingUser.setPhoneNumber(user.getPhoneNumber());

        changingUser.setBirthday(user.getBirthday());
        changingUser.setUsername(user.getUsername());
        changingUser.setAddress(user.getAddress());
        return userRepository.save(changingUser);
    }

    @Override
    public User changePassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = userRepository.findByEmail(email);
        User userToChange = user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userToChange.setPassword(passwordEncoder.encode(password));
        userRepository.save(userToChange);

        UserDetails newPrincipal = userDetailsService.loadUserByUsername(email);
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(), newPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        return userToChange;
    }

    @Override
    public void delete(String id) {
        if(!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserInfo(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user;
    }

}
