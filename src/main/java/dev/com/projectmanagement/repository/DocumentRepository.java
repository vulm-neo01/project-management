package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.Optional;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {

    Optional<Document> findByName(String name);
}
