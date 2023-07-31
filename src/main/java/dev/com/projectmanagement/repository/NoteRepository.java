package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Optional<Note>> findByNoteIdIn(List<String> noteIds);

    List<Note> findAllByCreatedBy(String createdBy);
}
