package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.*;
import dev.com.projectmanagement.model.request.MemberToManager;
import dev.com.projectmanagement.model.stable.NotiState;
import dev.com.projectmanagement.model.stable.NotificationType;
import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.repository.*;
import dev.com.projectmanagement.service.ProjectService;
import dev.com.projectmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectImpl implements ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final NotificationRepository notificationRepository;

    @Autowired
    private final TaskService taskService;

    @Override
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(String id){
        return projectRepository.findById(id);
    }

    @Override
    public Project createNew(Project project){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            // Đối tượng User tồn tại, bạn có thể lấy ra bằng phương thức get()
            User userUpdated = user.get();
            project.setCreatedBy(email);
            Map<String, Role> users = new HashMap<>();
            users.put(userUpdated.getUserId(), Role.MANAGER);
            project.setCreatedDate(LocalDate.now());
            project.setUsers(users);

            Project createdProject = projectRepository.save(project);
            userUpdated.getProjectIds().add(createdProject.getProjectId());
            repository.save(userUpdated);
            return createdProject;
        } else {
            throw new UsernameNotFoundException("Can find Id by Email!");
        }
    }

    @Override
    public Project updateInfo(Project project) {
        Optional<Project> changingProject = projectRepository.findById(project.getProjectId());

        Project projectToUpdate = changingProject.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        projectToUpdate.setDescription(project.getDescription());
        projectToUpdate.setProjectName(project.getProjectName());
        projectToUpdate.setProgress(project.getProgress());
        projectToUpdate.setStartDate(project.getStartDate());
        projectToUpdate.setEndDate(project.getEndDate());
        return projectRepository.save(projectToUpdate);
    }

    @Override
    public void delete(String id){
        if(!projectRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not existence!");
        }
        Project project = projectRepository.findById(id).get();
        List<String> taskIds = project.getTaskIds();
        for (String taskId : taskIds) {
            taskService.delete(taskId);
        }

        Set<String> userIds = project.getUsers().keySet();
        for (String key : userIds) {
            Optional<User> user = repository.findById(key);
            if(user.isPresent()){
                User userUpdated = user.get();
                userUpdated.getProjectIds().remove(id);
                repository.save(userUpdated);
            }
        }

        projectRepository.deleteById(id);
    }

    @Override
    public List<Optional<User>> findMemberById(String projectId, Role role) {
        Optional<Project> project = projectRepository.findById(projectId);
        Project updatedProject = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found!"));
        Map<String, Role> userList = updatedProject.getUsers();
        List<String> userIds = new ArrayList<>();

        for (Map.Entry<String, Role> entry : userList.entrySet()) {
            String userId = entry.getKey();
            Role userRole = entry.getValue();

            if (userRole == role) {
                userIds.add(userId);
            }
        }
        List<Optional<User>> users = repository.findByUserIdIn(userIds);
        return users;
    }

    @Override
    public List<String> findListMember(String projectId, Role role) {
        Optional<Project> project = projectRepository.findById(projectId);
        Project updatedProject = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found!"));
        Map<String, Role> userList = updatedProject.getUsers();
        List<String> userIds = new ArrayList<>();

        for (Map.Entry<String, Role> entry : userList.entrySet()) {
            String userId = entry.getKey();
            Role userRole = entry.getValue();

            if (userRole == role) {
                userIds.add(userId);
            }
        }
        return userIds;
    }

    @Override
    public List<Optional<Document>> findDocumentById(String projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        Project updatedProject = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found!"));

        List<String> docIds = updatedProject.getDocIds();

        List<Optional<Document>> docs = documentRepository.findByDocIdIn(docIds);
        return docs;
    }

    @Override
    public void deleteDocument(String docId) {
        Document document = documentRepository.findByDocId(docId);
        Optional<Project> project = projectRepository.findById(document.getRootId());
        Project updatedProject = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found!"));

        updatedProject.getDocIds().remove(docId);
        projectRepository.save(updatedProject);
        documentRepository.deleteById(docId);
    }

    @Override
    public String changeToManager(MemberToManager request) {
        String projectId = request.getProjectId();
        String userId = request.getUserId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<Project> project = projectRepository.findById(projectId);
        Project updatedProject = project.orElseThrow(() -> new RuntimeException("Can't find project by id!"));
        Optional<User> user = repository.findById(userId);

        Role currentRole = updatedProject.getUsers().get(userId);
        if(currentRole != null && currentRole == Role.MEMBER){
            updatedProject.getUsers().put(userId, Role.MANAGER);
        } else {
            throw new RuntimeException("User not have any role to change.");
        }

        projectRepository.save(updatedProject);

        Notification notification = new Notification();
        notification.setState(NotiState.UNREAD);
        notification.setProjectId(projectId);
        notification.setInviterName(email);
        notification.setRole(Role.MANAGER);
        notification.setType(NotificationType.NOTIFY);
        notification.setEmail(user.get().getEmail());
        notificationRepository.save(notification);

        return "Change to Manager successfully!";
    }
}
