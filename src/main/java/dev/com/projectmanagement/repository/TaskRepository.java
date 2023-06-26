package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
