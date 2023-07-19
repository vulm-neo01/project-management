package dev.com.projectmanagement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Note {
    @Id
    @Generated
    private String noteId;
    private String title;
    private String content;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private LocalDate alertTime;
    private String createdBy;

    public Note(String title, String content, LocalDate alertTime, String createdBy) {
        this.title = title;
        this.content = content;
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
        this.alertTime = alertTime;
        this.createdBy = String.valueOf(SecurityContextHolder.getContext().getAuthentication());
    }

    public Note(String title, LocalDate alertTime) {
        this.title = title;
        this.modifiedDate = LocalDate.now();
        this.alertTime = alertTime;
    }

    public Note(String content) {
        this.content = content;
        this.modifiedDate = LocalDate.now();
    }
}
