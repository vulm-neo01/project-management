package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    User findFirstByEmail(String email);

    List<Optional<User>> findByUserIdIn(List<String> userIds);

}
