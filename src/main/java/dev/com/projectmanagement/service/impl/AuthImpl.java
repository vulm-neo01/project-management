package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.dto.UserDTO;
import dev.com.projectmanagement.model.Note;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.register.RegisterRequest;
import dev.com.projectmanagement.model.request.MailRequest;
import dev.com.projectmanagement.model.stable.Priority;
import dev.com.projectmanagement.model.stable.Progress;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.model.stable.UserRole;
import dev.com.projectmanagement.repository.NoteRepository;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.TaskRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(RegisterRequest registerRequest) {
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFullName(registerRequest.getFullName());
        user.setJobPosition(registerRequest.getJobPosition());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setBirthday(registerRequest.getBirthday());
        user.setAddress(registerRequest.getAddress());
        user.setCreatedDate(LocalDateTime.now());
        user.setRole(UserRole.USER);

        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(createdUser.getEmail());
        userDTO.setUsername(createdUser.getUsername());
        userDTO.setFullName(createdUser.getFullName());
        userDTO.setJobPosition(createdUser.getJobPosition());
        userDTO.setPhoneNumber(createdUser.getPhoneNumber());
        userDTO.setBirthday(createdUser.getBirthday());
        userDTO.setAddress(createdUser.getAddress());

        Task task = new Task();
        task.setTaskName("New Task");
        task.setDescription("Task Description");
        task.setPriority(Priority.HIGH);
        task.setProgress(Progress.READY);
        task.setCreatedBy(createdUser.getEmail());
        task.setCreatedId(createdUser.getUserId());
        task.setCreatedDate(LocalDate.now());
        task.setStartDate(LocalDate.now());
        task.setEndDate(LocalDate.now().plusDays(2));
        task.setMemberIds(Collections.singletonList(createdUser.getUserId()));
        task.setDocIds(Collections.singletonList("64ae76d344d61231a7c82e02"));
//        task.getDiscussions().add("First Comment");

        Task createdTask = taskRepository.save(task);

        Project project = new Project();
        project.setProjectName("New Project");
        project.setDescription("Project Description");
        project.setProgress(Progress.READY);
        project.setCreatedBy(createdUser.getEmail());
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(2));
        project.setUsers((Map.of(createdUser.getUserId(), Role.MANAGER)));
        project.setDocIds(Collections.singletonList("64ae76d344d61231a7c82e02"));
        project.setTaskIds(Collections.singletonList(createdTask.getTaskId()));

        Project createdProject = projectRepository.save(project);

        task.setProjectId(createdProject.getProjectId());
        taskRepository.save(task);

        Note note = new Note();
        note.setTitle("Note Title");
        note.setContent("This note is using to remember work!");
        note.setCreatedBy(createdUser.getEmail());
        note.setUserId(createdUser.getUserId());
        note.setCreatedDate(LocalDate.now());
        note.setModifiedDate(LocalDate.now());
        note.setAlertTime(LocalDateTime.now().plusDays(2));
        note.setProject(createdProject);
        note.setTask(createdTask);

        Note createdNote = noteRepository.save(note);

        user.setProjectIds(Collections.singletonList(createdProject.getProjectId()));
        user.setTaskIds(Collections.singletonList(createdTask.getTaskId()));
        user.setNoteIds(Collections.singletonList(createdNote.getNoteId()));

        userRepository.save(user);
        return userDTO;
    }

    @Override
    public boolean checkEmail(MailRequest email) {

        Optional<User> userEmail = userRepository.findByEmail(email.getEmail());
        return userEmail.isPresent(); // Trả về true nếu email đã tồn tại và false nếu email không tồn tại
    }

    // Thêm hàm check trùng username, để tránh người dùng bị trùng khi muốn thêm vào làm member của project

}
