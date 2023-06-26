package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface DocumentService {

    List<Document> findAll();

    Optional<Document> findById(String id);

    String insertNew(Document document);

    String update(Document document);

    void delete(String id);
}
