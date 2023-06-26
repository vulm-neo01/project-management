package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {

    Optional<Project> findById(String projectId);
}
