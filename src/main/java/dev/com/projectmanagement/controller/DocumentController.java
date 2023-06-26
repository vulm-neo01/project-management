package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Document;
import dev.com.projectmanagement.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    @Autowired
    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocument(){
        return ResponseEntity.ok(documentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Document>> getDocument(@PathVariable("id") String documentId){
        return ResponseEntity.ok(documentService.findById(documentId));
    }

    @PostMapping
    public ResponseEntity<String> saveDocument(@RequestBody Document document){
        return ResponseEntity.ok(documentService.insertNew(document));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateDocument(@RequestBody Document document){
        return ResponseEntity.ok(documentService.update(document));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable("id") String documentId){
        documentService.delete(documentId);
        return ResponseEntity.accepted().build();
    }
}
