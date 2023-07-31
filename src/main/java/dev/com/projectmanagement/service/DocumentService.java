package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.print.Doc;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface DocumentService {

//    List<Document> findAll();
//
//    Optional<Document> findById(String id);
//
//    String insertNew(Document document);
//
//    String update(Document document);
//
//    void delete(String id);

    String storeToProject(MultipartFile file, String projectId) throws IOException;

    String storeToTask(MultipartFile file, String taskId) throws IOException;
    byte[] downloadFile(String id);

    Document getFile(String id);

    Stream<Document> getAllFiles();


//    Path load(String filename);
//
//    Resource loadAsResource(String filename);
//
//    Stream<Path> loadAll();
//
//    void deleteAll();
//
//    void init();
}
