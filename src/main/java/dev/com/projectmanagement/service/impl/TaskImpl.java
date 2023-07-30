package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.TaskRepository;
import dev.com.projectmanagement.repository.UserRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskImpl implements TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final ProjectRepository projectRepository;

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
        taskRepository.deleteById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if(user.isPresent()){
            User userUpdated = user.get();
            userUpdated.getTaskIds().remove(id);
            repository.save(userUpdated);
        }
    }
}
