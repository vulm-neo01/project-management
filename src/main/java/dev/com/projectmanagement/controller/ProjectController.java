package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    @Autowired
    private final ProjectService projectService;

    
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping
    public ResponseEntity<List<Project>> getAllProject(){
        logger.info(projectService.findAll().toString());
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> getProject(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.findById(projectId));
    }


    @CrossOrigin
    @PostMapping
    public ResponseEntity<Project> saveProject(@RequestBody Project project){
        return ResponseEntity.ok(projectService.createNew(project));
    }

    @PostMapping("/update")
    public ResponseEntity<Project> updateProject(@RequestBody Project project){
        return ResponseEntity.ok(projectService.updateInfo(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") String projectId){
        projectService.delete(projectId);
        return ResponseEntity.accepted().build();
    }
}
