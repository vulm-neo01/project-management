package dev.com.projectmanagement;

import dev.com.projectmanagement.model.*;
import dev.com.projectmanagement.model.stable.Priority;
import dev.com.projectmanagement.repository.*;
import dev.com.projectmanagement.service.DocumentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class ProjectManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(DocumentService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}
}
