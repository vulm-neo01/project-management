package dev.com.projectmanagement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Note {
    @Id
    @Generated
    private String noteId;
    private String userId;
    private String title;
    private String content;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private LocalDateTime alertTime;
    private Boolean isAlert;
    private String createdBy;
    private Project project;
    private Task task;

    public Note(String title, String content, LocalDateTime alertTime, String createdBy) {
        this.title = title;
        this.content = content;
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
        this.alertTime = alertTime;
        this.createdBy = String.valueOf(SecurityContextHolder.getContext().getAuthentication());
    }

    public Note(String title, LocalDateTime alertTime) {
        this.title = title;
        this.modifiedDate = LocalDate.now();
        this.alertTime = alertTime;
    }

    public Note(String content) {
        this.content = content;
        this.modifiedDate = LocalDate.now();
    }

    public Note(String noteId, String content) {
        this.noteId = noteId;
        this.content = content;
    }

    public Note(String title, LocalDateTime alertTime, Project project, Task task) {
        this.title = title;
        this.alertTime = alertTime;
        this.project = project;
        this.task = task;
    }
}
