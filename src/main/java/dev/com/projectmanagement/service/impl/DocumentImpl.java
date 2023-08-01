package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.repository.DocumentRepository;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.TaskRepository;
import dev.com.projectmanagement.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DocumentImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Override
    public String storeToProject(MultipartFile file, String projectId) throws IOException {
        Document document = documentRepository.save(Document.builder()
                        .name(file.getOriginalFilename())
                        .rootId(projectId) // Cần sửa lại, test tạm thời
                        .description("")
                        .docData(file.getBytes())
                        .type(file.getContentType())
                        .uploadBy(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                        .uploadDate(LocalDate.now())
                .build());

        Optional<Project> changingProject = projectRepository.findById(projectId);
        Project projectToUpdate = changingProject.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        projectToUpdate.getDocIds().add(document.getDocId());
        projectRepository.save(projectToUpdate);

        if(document != null){
            return "file upload successfully: " + file.getOriginalFilename();
        }
        return "File upload fail";
    }

    @Override
    public String storeToTask(MultipartFile file, String taskId) throws IOException {
        Document document = documentRepository.save(Document.builder()
                .name(file.getOriginalFilename())
                .rootId(taskId) // Cần sửa lại, test tạm thời
                .description("")
                .docData(file.getBytes())
                .type(file.getContentType())
                .uploadBy(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .uploadDate(LocalDate.now())
                .build());

        Optional<Task> changingTask = taskRepository.findById(taskId);
        Task updatedTask = changingTask.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        updatedTask.getDocIds().add(document.getDocId());
        taskRepository.save(updatedTask);

        if(document != null){
            return "file upload successfully: " + file.getOriginalFilename();
        }
        return "File upload fail";
    }

    @Override
    public byte[] downloadFile(String id){
        Optional<Document> doc = documentRepository.findById(id);
        byte[] file = doc.get().getDocData();

        return file;
    }

    @Override
    public Document getFile(String id){
        return documentRepository.findById(id).get();
    };

    @Override
    public Stream<Document> getAllFiles(){
        return documentRepository.findAll().stream();
    }
}
