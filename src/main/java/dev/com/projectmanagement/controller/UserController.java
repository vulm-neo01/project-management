package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.login.Login;
import dev.com.projectmanagement.model.login.LoginMessage;
import dev.com.projectmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllAccount(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getAccount(@PathVariable("id") String userId){
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    public ResponseEntity<User> saveAccount(@RequestBody User user){
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateProfile(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PostMapping("/password")
    public ResponseEntity<User> changePassword(@RequestBody User user){
        return ResponseEntity.ok(userService.changePassword(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") String userId){
        userService.delete(userId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginMessage> login(@RequestBody Login login){
        LoginMessage loginMessage = userService.loginUser(login);
        return ResponseEntity.ok(loginMessage);
    }
}
