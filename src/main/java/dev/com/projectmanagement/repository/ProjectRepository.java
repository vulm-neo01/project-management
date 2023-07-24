package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    Optional<Project> findById(String projectId);

    List<Optional<Project>> findByProjectIdIn(List<String> projectIds);
}
