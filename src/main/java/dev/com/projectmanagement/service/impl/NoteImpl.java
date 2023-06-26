package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.repository.NoteRepository;
import dev.com.projectmanagement.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteImpl implements NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    @Override
    public List<Note> findAll(){
        return noteRepository.findAll();
    }

    @Override
    public Optional<Note> findById(String id){
        return noteRepository.findById(id);
    }

    @Override
    public String insertNote(Note note){
        return noteRepository.save(note).toString();
    }

    @Override
    public String updateSetting(Note note) {
        Optional<Note> changingNote = noteRepository.findById(note.getNoteId());
        Note noteUpdated = changingNote.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        noteUpdated.setTitle(note.getTitle());
        noteUpdated.setAlertTime(note.getAlertTime());

        return noteRepository.save(noteUpdated).toString();
    }

    @Override
    public String updateContent(Note note) {
        Optional<Note> changingNote = noteRepository.findById(note.getNoteId());
        Note noteUpdated = changingNote.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        noteUpdated.setContent(note.getContent());

        return noteRepository.save(noteUpdated).toString();
    }

    @Override
    public void delete(String id){
        if(!noteRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
        }
        noteRepository.deleteById(id);
    }
}
