package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.ProjectService;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectImpl implements ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final UserRepository repository;

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
            project.setCreatedBy(userUpdated.getFullName());
//            project.setUser(userUpdated.getEmail());
            project.setCreatedDate(LocalDate.now());
            project.setStartDate(LocalDate.now());
            project.setEndDate(LocalDate.now().plusDays(5));

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
        projectRepository.deleteById(id);
    }
}
