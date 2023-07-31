package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.repository.NoteRepository;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.TaskRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final NoteRepository noteRepository;

//    @GetMapping
//    public ResponseEntity<List<User>> getAllAccount(){
//        return ResponseEntity.ok(userService.findAll());
//    }

    @GetMapping
    public ResponseEntity<Optional<User>> getAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            // Đối tượng User tồn tại, bạn có thể lấy ra bằng phương thức get()
            String userId = user.get().getUserId();
            return ResponseEntity.ok(userService.findById(userId));
        } else {
            throw new UsernameNotFoundException("Can find Id by Email!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getAnotherInfo(@PathVariable("id") String userId){
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }



    @GetMapping("/projects")
    public ResponseEntity<List<Optional<Project>>> getProjects(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            // Đối tượng User tồn tại, bạn có thể lấy ra bằng phương thức get()
            List<String> projectIds = user.get().getProjectIds();
            List<Optional<Project>> projects = projectRepository.findByProjectIdIn(projectIds);
            return ResponseEntity.ok(projects);
        } else {
            throw new UsernameNotFoundException("Can find Id by Email!");
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Optional<Task>>> getTasks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            // Đối tượng User tồn tại, bạn có thể lấy ra bằng phương thức get()
            List<String> taskIds = user.get().getTaskIds();
            List<Optional<Task>> tasks = taskRepository.findByTaskIdIn(taskIds);
            return ResponseEntity.ok(tasks);
        } else {
            throw new UsernameNotFoundException("Can find Id by Email!");
        }
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Optional<Note>>> getNotes(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            // Đối tượng User tồn tại, bạn có thể lấy ra bằng phương thức get()
            List<String> noteIds = user.get().getNoteIds();
            List<Optional<Note>> notes = noteRepository.findByNoteIdIn(noteIds);
            return ResponseEntity.ok(notes);
        } else {
            throw new UsernameNotFoundException("Can find Id by Email!");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateProfile(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PostMapping("/password")
    public ResponseEntity<User> changePassword(@RequestBody String password){
        return ResponseEntity.ok(userService.changePassword(password));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") String userId){
        userService.delete(userId);
        return ResponseEntity.accepted().build();
    }

}
