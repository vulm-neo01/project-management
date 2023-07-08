package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.print.Doc;
import java.util.Optional;

public interface DocumentRepository extends MongoRepository<Document, String> {

    Optional<Document> findByName(String name);
}
