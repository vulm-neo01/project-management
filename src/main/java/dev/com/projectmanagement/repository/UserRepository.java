package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findOneByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
