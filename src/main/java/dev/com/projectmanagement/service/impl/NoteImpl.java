package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.request.NoteContentRequest;
import dev.com.projectmanagement.model.request.NoteInfoRequest;
import dev.com.projectmanagement.repository.NoteRepository;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.TaskRepository;
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

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final TaskRepository taskRepository;

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
    public Optional<Note> updateSetting(NoteInfoRequest request) {
        Optional<Note> changingNote = noteRepository.findById(request.getNoteId());
        Note noteUpdated = changingNote.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        if(request.getProjectId() != null){
            Optional<Project> project = projectRepository.findById(request.getProjectId());
            noteUpdated.setProject(project.get());
        } else {
            noteUpdated.setProject(null);
        }
        if(request.getTaskId() != null){
            Optional<Task> task = taskRepository.findById(request.getTaskId());
            noteUpdated.setTask(task.get());
        } else {
            noteUpdated.setTask(null);
        }
        noteUpdated.setTitle(request.getTitle());
        noteUpdated.setAlertTime(request.getAlertTime());
        noteUpdated.setModifiedDate(LocalDate.now());

        return Optional.of(noteRepository.save(noteUpdated));
    }

    @Override
    public Optional<Note> updateContent(NoteContentRequest request) {
        Optional<Note> changingNote = noteRepository.findById(request.getNoteId());
        Note noteUpdated = changingNote.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        noteUpdated.setContent(request.getContent());
        noteUpdated.setModifiedDate(LocalDate.now());

        return Optional.of(noteRepository.save(noteUpdated));
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
