package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<Note> findAll();

    Optional<Note> findById(String id);

    Note insertNote(Note note);

    String updateSetting(Note note);

    String updateContent(Note note);

    void delete(String id);
}
