package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    @Autowired
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNote(){
        return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Note>> getNote(@PathVariable("id") String noteId){
        return ResponseEntity.ok(noteService.findById(noteId));
    }

    @PostMapping
    public ResponseEntity<String> saveNote(@RequestBody Note note){
        return ResponseEntity.ok(noteService.insertNote(note));
    }

    @PostMapping("/updateSetting")
    public ResponseEntity<String> changingSetting(@RequestBody Note note){
        return ResponseEntity.ok(noteService.updateSetting(note));
    }

    @PostMapping("/updateContent")
    public ResponseEntity<String> changingContent(@RequestBody Note note){
        return ResponseEntity.ok(noteService.updateContent(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") String noteId){
        noteService.delete(noteId);
        return ResponseEntity.accepted().build();
    }
}
