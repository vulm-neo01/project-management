package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.login.Login;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.login.LoginMessage;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(String id);

    User saveUser(User user);

    User updateUser(User user);

    User changePassword(User user);

    void delete(String id);

    LoginMessage loginUser(Login login);
}
