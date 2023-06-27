package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.request.AddDocRequest;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.model.request.AddUserRequest;
import dev.com.projectmanagement.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BaseController {

    @Autowired
    private BaseService baseService;

    @PostMapping("/addProjectUser")
    public ResponseEntity<String> addUserToProject(@RequestBody AddUserRequest request){
        String userId = request.getUserId();
        String projectId = request.getProjectId();
        Role role = request.getRole();

        return ResponseEntity.ok(baseService.addUserToProject(userId, projectId, role));
    }

    @PostMapping("/addTaskMember")
    public ResponseEntity<String> addMemberToTask(@RequestBody AddUserRequest request){
        String userId = request.getUserId();
        String taskId = request.getTaskId();

        return ResponseEntity.ok(baseService.addUserToTask(userId, taskId));
    }

    @PostMapping("/addDocumentProject")
    public ResponseEntity<String> addDocumentProject(@RequestBody AddDocRequest request){
        String docId = request.getRequestId();
        String projectId = request.getDestinationId();

        return ResponseEntity.ok(baseService.addDocumentProject(docId, projectId));
    }

    @PostMapping("/addDocumentTask")
    public ResponseEntity<String> addDocumentTask(@RequestBody AddDocRequest request){
        String docId = request.getRequestId();
        String taskId = request.getDestinationId();

        return ResponseEntity.ok(baseService.addDocumentTask(docId, taskId));
    }

    @PostMapping("/shareNote")
    public ResponseEntity<String> shareNote(@RequestBody AddDocRequest request){
        String noteId = request.getRequestId();
        String userId = request.getDestinationId();

        return ResponseEntity.ok(baseService.shareNote(noteId, userId));
    }
}
