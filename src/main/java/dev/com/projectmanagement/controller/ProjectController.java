package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    @Autowired
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProject(){
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> getProject(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.findById(projectId));
    }

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
