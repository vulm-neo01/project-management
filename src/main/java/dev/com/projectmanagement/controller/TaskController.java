package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.request.MemberToManager;
import dev.com.projectmanagement.model.request.TaskRequest;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    @Autowired
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTask(@PathVariable("id") String taskId){
        return ResponseEntity.ok(taskService.findById(taskId));
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<List<Optional<User>>> getListMember(@PathVariable("id") String taskId){
        return ResponseEntity.ok(taskService.findMemberById(taskId));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<Optional<Task>>> getTaskList(@PathVariable("id") String projectId){
        return ResponseEntity.ok(taskService.findListById(projectId));
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody Task task){
        return ResponseEntity.ok(taskService.createNew(task));
    }

    @PostMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        return ResponseEntity.ok(taskService.updateInfo(task));
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFromTask(@RequestBody TaskRequest request){
        return ResponseEntity.ok(taskService.removeFromTask(request));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToTask(@RequestBody TaskRequest request){
        return ResponseEntity.ok(taskService.addToTask(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String taskId){
        taskService.delete(taskId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<List<Optional<Document>>> getListDocument(@PathVariable("id") String taskId){
        return ResponseEntity.ok(taskService.findDocumentById(taskId));
    }

    @DeleteMapping("/documents/delete/{id}")
    public void  deleteDocument(@PathVariable("id") String docId){
        taskService.deleteDocument(docId);
    }
}
