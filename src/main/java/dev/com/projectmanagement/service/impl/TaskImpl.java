package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.repository.TaskRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskImpl implements TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(String id){
        return taskRepository.findById(id);
    }

    @Override
    public Task createNew(Task task){
        return taskRepository.insert(task);
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
    }
}
