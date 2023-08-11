package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.*;
import dev.com.projectmanagement.model.request.TaskRequest;
import dev.com.projectmanagement.model.stable.NotiState;
import dev.com.projectmanagement.model.stable.NotificationType;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.repository.*;
import dev.com.projectmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskImpl implements TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final NotificationRepository notificationRepository;

    @Override
    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(String id){
        return taskRepository.findById(id);
    }

    @Override
    public List<Optional<Task>> findListById(String projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        Project updatedProject = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found!"));
        List<String> taskIds = updatedProject.getTaskIds();
        List<Optional<Task>> tasks = taskRepository.findByTaskIdIn(taskIds);
        return tasks;
    }

    @Override
    public Task createNew(Task task){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Optional<Project> project = projectRepository.findById(task.getProjectId());
        Project updatedProject = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found!"));
        Optional<User> user = repository.findByEmail(email);
        User updatedUser = user.orElseThrow(() -> new UsernameNotFoundException("Not found user created task!"));

        task.setCreatedDate(LocalDate.now());
        Task createdTask = taskRepository.save(task);

        updatedProject.getTaskIds().add(createdTask.getTaskId());
        projectRepository.save(updatedProject);
        return createdTask;
    }

    @Override
    public Task updateInfo(Task task) {
        Optional<Task> changingTask = taskRepository.findById(task.getTaskId());

        Task taskUpdated = changingTask.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskUpdated.setTaskName(task.getTaskName());
        taskUpdated.setDescription(task.getDescription());
        taskUpdated.setPriority(task.getPriority());
        taskUpdated.setProgress(task.getProgress());
        taskUpdated.setStartDate(task.getStartDate());
        taskUpdated.setEndDate(task.getEndDate());

        return taskRepository.save(taskUpdated);
    }

    @Override
    public void delete(String id){
        if(!taskRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
        }
        Task task = taskRepository.findById(id).get();
        List<String> userIds = task.getMemberIds();

        for(String userId : userIds){
            Optional<User> user = repository.findById(userId);
            if(user.isPresent()){
                User userUpdated = user.get();
                userUpdated.getTaskIds().remove(id);
                repository.save(userUpdated);
            }
        }

        taskRepository.deleteById(id);
    }

    @Override
    public List<Optional<User>> findMemberById(String taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        Task updatedTask = task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found!"));

        List<String> memberIds = updatedTask.getMemberIds();


        List<Optional<User>> users = repository.findByUserIdIn(memberIds);
        return users;
    }

    @Override
    public List<Optional<Document>> findDocumentById(String taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        Task updatedTask = task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found!"));

        List<String> docIds = updatedTask.getDocIds();

        List<Optional<Document>> docs = documentRepository.findByDocIdIn(docIds);
        return docs;
    }

    @Override
    public void deleteDocument(String docId) {
        Document document = documentRepository.findByDocId(docId);
        Optional<Task> task = taskRepository.findById(document.getRootId());
        Task updatedTask = task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found!"));

        updatedTask.getDocIds().remove(docId);
        taskRepository.save(updatedTask);
        documentRepository.deleteById(docId);
    }

    @Override
    public String removeFromTask(TaskRequest request) {
        String taskId = request.getTaskId();
        String userId = request.getUserId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<Task> task = taskRepository.findById(taskId);
        Task updatedTask = task.orElseThrow(() -> new RuntimeException("Can't find task by id!"));
        Optional<User> user = repository.findById(userId);
        User updatedUser = user.orElseThrow(() -> new RuntimeException("Can't find user by id!"));

        updatedTask.getMemberIds().remove(userId);
        updatedUser.getTaskIds().remove(taskId);

        taskRepository.save(updatedTask);
        repository.save(updatedUser);

        Notification notification = new Notification();
        notification.setState(NotiState.UNREAD);
        notification.setTaskId(taskId);
        notification.setInviterName(email);
        notification.setType(NotificationType.NOTIFY);
        notification.setEmail(user.get().getEmail());
        notificationRepository.save(notification);

        return "Remove from task successfully!";
    }

    @Override
    public String addToTask(TaskRequest request) {
        String taskId = request.getTaskId();
        String userId = request.getUserId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<Task> task = taskRepository.findById(taskId);
        Task updatedTask = task.orElseThrow(() -> new RuntimeException("Can't find task by id!"));
        Optional<User> user = repository.findById(userId);
        User updatedUser = user.orElseThrow(() -> new RuntimeException("Can't find user by id!"));

        updatedTask.getMemberIds().add(userId);
        updatedUser.getTaskIds().add(taskId);

        taskRepository.save(updatedTask);
        repository.save(updatedUser);

        Notification notification = new Notification();
        notification.setState(NotiState.UNREAD);
        notification.setTaskId(taskId);
        notification.setInviterName(email);
        notification.setType(NotificationType.NOTIFY);
        notification.setEmail(user.get().getEmail());
        notificationRepository.save(notification);

        return "Add to task successfully!";
    }
}
