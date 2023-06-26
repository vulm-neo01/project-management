package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.login.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Login, String> {
}
