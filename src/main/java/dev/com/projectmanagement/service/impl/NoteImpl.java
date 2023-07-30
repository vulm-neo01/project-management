package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.repository.NoteRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteImpl implements NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    @Autowired
    private final UserRepository repository;

    @Override
    public List<Note> findAll(){
        return noteRepository.findAll();
    }

    @Override
    public Optional<Note> findById(String id){
        return noteRepository.findById(id);
    }

    @Override
    public Note insertNote(Note note){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            User userUpdated = user.get();
            note.setCreatedBy(email);
            note.setCreatedDate(LocalDate.now());
            note.setModifiedDate(LocalDate.now());
            note.setUserId(userUpdated.getUserId());

            Note createdNote = noteRepository.save(note);
            userUpdated.getNoteIds().add(createdNote.getNoteId());
            repository.save(userUpdated);
            return createdNote;
        } else {
            throw new UsernameNotFoundException("Can find Id by Email!");
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if(user.isPresent()){
            User userUpdated = user.get();
            userUpdated.getNoteIds().remove(id);
            repository.save(userUpdated);
        }
    }
}
