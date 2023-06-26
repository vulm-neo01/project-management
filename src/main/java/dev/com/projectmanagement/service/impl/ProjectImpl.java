package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectImpl implements ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Override
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(String id){
        return projectRepository.findById(id);
    }

    @Override
    public Project createNew(Project project){
        return projectRepository.insert(project);
    }

    @Override
    public Project updateInfo(Project project) {
        Optional<Project> changingProject = projectRepository.findById(project.getProjectId());

        Project projectToUpdate = changingProject.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        projectToUpdate.setDescription(project.getDescription());
        projectToUpdate.setProjectName(project.getProjectName());
        projectToUpdate.setProgress(project.getProgress());
        projectToUpdate.setStartDate(project.getStartDate());
        projectToUpdate.setEndDate(project.getEndDate());
        return projectRepository.save(projectToUpdate);
    }

    @Override
    public void delete(String id){
        if(!projectRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
        }
        projectRepository.deleteById(id);
    }
}
