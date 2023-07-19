package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.login.LoginRequest;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.login.LoginMessage;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

//    @Override
//    public User saveUser(User user) {
//        if(userRepository.findByEmail(user.getEmail()) != null){
//            throw  new RuntimeException("Email existence!");
//        }
//        String encodePassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodePassword);
//        return userRepository.insert(user);
//    }

    @Override
    public User updateUser(User user) {
        Optional<User> userToChange = userRepository.findByEmail(user.getEmail());
        User changingUser = userToChange.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(changingUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(user.getFullName() != ""){
            changingUser.setFullName(user.getFullName());
        }
        if(user.getJobPosition() != ""){
            changingUser.setJobPosition(user.getJobPosition());
        }
        if(user.getPhoneNumber() != ""){
            changingUser.setPhoneNumber(user.getPhoneNumber());
        }

        changingUser.setBirthday(user.getBirthday());

        if(user.getAddress() != ""){
            changingUser.setAddress(user.getAddress());
        }
        return userRepository.save(changingUser);
//        return null;
    }

    @Override
    public User changePassword(User user) {
        Optional<User> changingUser = userRepository.findByEmail(user.getEmail());
        User userToChange = changingUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(changingUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(user.getPassword() != null){
            userToChange.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(userToChange);
    }

    @Override
    public void delete(String id) {
        if(!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
        }
        userRepository.deleteById(id);
    }

//    @Override
//    public LoginMessage loginUser(LoginRequest loginRequest) {
//        String msg = "";
//        Optional<User> loginUser = userRepository.findByEmail(loginRequest.getEmail());
//        User user = loginUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        if(user != null){
//            String password = loginRequest.getPassword();
//            String encodePassword = user.getPassword();
//            Boolean isRightPassword = passwordEncoder.matches(password, encodePassword);
//            if(isRightPassword){
//                Optional<User> user1 = userRepository.findOneByEmailAndPassword(loginRequest.getEmail(), encodePassword);
//                if(user1.isPresent()){
//                    return new LoginMessage("Login success", true);
//                } else {
//                    return new LoginMessage("Login Failed", false);
//                }
//            } else {
//                return new LoginMessage("Password not match", false);
//            }
//        } else {
//            return new LoginMessage("Email not exists!", false);
//        }
//    }

}
