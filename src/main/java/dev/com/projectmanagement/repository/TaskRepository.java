package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Optional<Task>> findByTaskIdIn(List<String> taskIds);

    void deleteAllByProjectId(String projectId);
}
