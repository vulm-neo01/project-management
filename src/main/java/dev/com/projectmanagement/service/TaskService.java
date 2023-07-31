package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.request.TaskRequest;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll();

    Optional<Task> findById(String id);

    List<Optional<Task>> findListById(String projectId);

    Task createNew(Task task);

    Task updateInfo(Task task);

    void delete(String id);

    List<Optional<User>> findMemberById(String taskId);

    List<Optional<Document>> findDocumentById(String taskId);

    void deleteDocument(String docId);

    String removeFromTask(TaskRequest request);

    String addToTask(TaskRequest request);
}
