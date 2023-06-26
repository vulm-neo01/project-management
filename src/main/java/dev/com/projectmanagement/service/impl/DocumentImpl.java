package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.repository.DocumentRepository;
import dev.com.projectmanagement.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentImpl implements DocumentService {
    @Autowired
    private final DocumentRepository documentRepository;

    @Override
    public List<Document> findAll(){
        return documentRepository.findAll();
    }

    @Override
    public Optional<Document> findById(String id){
        return documentRepository.findById(id);
    }

    @Override
    public String insertNew(Document document){
        return documentRepository.save(document).toString();
    }

    @Override
    public String update(Document document) {
        Optional<Document> changingDoc = documentRepository.findById(document.getDocId());
        Document documentUpdated = changingDoc.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        documentUpdated.setDescription(document.getDescription());

        return documentRepository.save(documentUpdated).toString();
    }

    @Override
    public void delete(String id){
        if(!documentRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
        }
        documentRepository.deleteById(id);
    }
}
