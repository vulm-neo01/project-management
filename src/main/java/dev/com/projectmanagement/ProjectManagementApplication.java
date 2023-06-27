package dev.com.projectmanagement;

import dev.com.projectmanagement.model.*;
import dev.com.projectmanagement.model.stable.Priority;
import dev.com.projectmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class ProjectManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApplication.class, args);
	}

//	@Bean
	CommandLineRunner commandLineRunner(ProjectRepository projectRepository, TaskRepository taskRepository, NoteRepository noteRepository, DocumentRepository documentRepository){
		return args -> {
			LocalDate date = LocalDate.from(LocalDateTime.of(2023,6,15,12,30));
			projectRepository.insert(new Project("description", "project name", "Le Minh Vu"));
			taskRepository.insert(new Task("description", "task name", Priority.HIGH, "Le Minh Vu"));
			noteRepository.insert(new Note("title", "content here", date, "Le Minh Vu"));
			documentRepository.insert(new Document("description", "Le Minh Vu"));
		};
	}
}
