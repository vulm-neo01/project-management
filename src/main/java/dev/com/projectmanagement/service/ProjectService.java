package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
public interface ProjectService {

    List<Project> findAll();

    Optional<Project> findById(String id);

    Project createNew(Project project);

    Project updateInfo(Project project);

    void delete(String id);
}
