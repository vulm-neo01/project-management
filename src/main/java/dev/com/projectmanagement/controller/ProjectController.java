package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.request.MemberToManager;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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



    @PostMapping
    public ResponseEntity< Project> saveProject(@RequestBody Project project){
        logger.info("Create project with data: " + project);
        return ResponseEntity.ok(projectService.createNew(project));
    }

    @PostMapping("/update")
    public ResponseEntity<Project> updateProject(@RequestBody Project project){
        return ResponseEntity.ok(projectService.updateInfo(project));
    }

    @PostMapping("/memberToManager")
    public ResponseEntity<String> updateMemberToManager(@RequestBody MemberToManager request){
        return ResponseEntity.ok(projectService.changeToManager(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") String projectId){
        projectService.delete(projectId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<List<Optional<User>>> getListMember(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.findMemberById(projectId, Role.MEMBER));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Optional<User>>> getListUsers(@PathVariable("id") String projectId){
        List<Optional<User>> members = projectService.findMemberById(projectId, Role.MEMBER);
        List<Optional<User>> managers = projectService.findMemberById(projectId, Role.MANAGER);

        List<Optional<User>> users = Stream.concat(members.stream(), managers.stream())
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/memberIds/{id}")
    public ResponseEntity<List<String>> getListMemberId(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.findListMember(projectId, Role.MEMBER));
    }

    @GetMapping("/managers/{id}")
    public ResponseEntity<List<Optional<User>>> getListManager(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.findMemberById(projectId, Role.MANAGER));
    }

    @GetMapping("/managerIds/{id}")
    public ResponseEntity<List<String>> getListManagerId(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.findListMember(projectId, Role.MANAGER));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<List<Optional<Document>>> getListDocument(@PathVariable("id") String projectId){
        return ResponseEntity.ok(projectService.findDocumentById(projectId));
    }

    @DeleteMapping("/documents/delete/{id}")
    public void  deleteDocument(@PathVariable("id") String docId){
        projectService.deleteDocument(docId);
    }

}
