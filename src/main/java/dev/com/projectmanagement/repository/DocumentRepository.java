package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<Document, String> {
}
