package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.*;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    NoteRepository noteRepository;

    public String addUserToProject(String userId, String projectId, Role role){
        Optional<User> user = userRepository.findById(userId);
        User userToAdd = user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Optional<Project> project = projectRepository.findById(projectId);
        Project projectToAdd = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        List<String> projectIds = user.get().getProjectIds();
        if(projectIds == null){
            projectIds = new ArrayList<>();
        }
        if (projectIds.contains(projectToAdd.getProjectId())) {
            throw new DuplicateKeyException("ProjectId already exists");
        }
        projectIds.add(projectToAdd.getProjectId());
        userToAdd.setProjectIds(projectIds);
        userRepository.save(userToAdd);

        Map<String, Role> userRoles = project.get().getUsers();
        if(userRoles == null){
            userRoles = new HashMap<String, Role>();
        }
        if (userRoles.keySet().contains(userToAdd.getUserId())) {
            throw new DuplicateKeyException("UserId already exists");
        }

        userRoles.put(userToAdd.getUserId(), role);
        projectToAdd.setUsers(userRoles);
        projectRepository.save(projectToAdd);

        return userToAdd.toString() + "/n" + projectToAdd.toString();
    }

    public String addUserToTask(String userId, String taskId){
        Optional<User> user = userRepository.findById(userId);
        User userToAdd = user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Optional<Task> task = taskRepository.findById(taskId);
        Task taskToAdd = task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        List<String> taskIds = user.get().getTaskIds();
        if(taskIds == null){
            taskIds = new ArrayList<>();
        }
        if (taskIds.contains(taskToAdd.getTaskId())) {
            throw new DuplicateKeyException("TaskId already exists");
        }
        taskIds.add(taskToAdd.getTaskId());
        userToAdd.setTaskIds(taskIds);
        userRepository.save(userToAdd);

        List<String> userIds = taskToAdd.getMemberIds();
        if(userIds == null){
            userIds = new ArrayList<>();
        }
        if (userIds.contains(userToAdd.getUserId())) {
            throw new DuplicateKeyException("UserId already exists");
        }

        userIds.add(userToAdd.getUserId());
        taskToAdd.setMemberIds(userIds);
        taskRepository.save(taskToAdd);

        return userToAdd.toString() + "/n" + taskToAdd.toString();
    }

    public String addDocumentProject(String docId, String projectId){

        Optional<Document> document = documentRepository.findById(docId);
        Document documentToAdd = document.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        Optional<Project> project = projectRepository.findById(projectId);
        Project projectToAdd = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        List<String> docIds = projectToAdd.getDocIds();
        if(docIds == null){
            docIds = new ArrayList<>();
        }
        if(docIds.contains(docId)){
            throw new DuplicateKeyException("Document already exists");
        }

        docIds.add(docId);
        projectToAdd.setDocIds(docIds);
        projectRepository.save(projectToAdd);
        return projectToAdd.toString() + " + " + documentToAdd.toString();
    }

    public String addDocumentTask(String docId, String taskId){

        Optional<Document> document = documentRepository.findById(docId);
        Document documentToAdd = document.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        Optional<Task> task = taskRepository.findById(taskId);
        Task taskToAdd = task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        List<String> docIds = taskToAdd.getDocIds();
        if(docIds == null){
            docIds = new ArrayList<>();
        }
        if(docIds.contains(docId)){
            throw new DuplicateKeyException("Document already exists");
        }

        docIds.add(docId);
        taskToAdd.setDocIds(docIds);
        taskRepository.save(taskToAdd);
        return taskToAdd.toString() + " + " + documentToAdd.toString();
    }

    public String shareNote(String noteId, String userId){
        Optional<Note> note = noteRepository.findById(noteId);
        Note Note = note.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        Optional<User> user = userRepository.findById(userId);
        User userToAdd = user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        List<String> noteIds = userToAdd.getNoteIds();
        if(noteIds == null){
            noteIds = new ArrayList<>();
        }
        if(noteIds.contains(noteId)){
            throw new DuplicateKeyException("Note already exists");
        }

        noteIds.add(noteId);
        userToAdd.setNoteIds(noteIds);
        userRepository.save(userToAdd);
        return userToAdd.toString();
    }
}
