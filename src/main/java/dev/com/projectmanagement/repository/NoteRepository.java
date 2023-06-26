package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
}
