package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll();

    Optional<Task> findById(String id);

    Task createNew(Task task);

    Task updateInfo(Task task);

    void delete(String id);
}
