package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.login.LoginRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<LoginRequest, String> {
}
