package dev.com.projectmanagement.model;

import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @Generated
    private String taskId;
    private String description;
    private String taskName;
    private Priority priority;
    private Progress progress;
    private LocalDate createdDate;
    private String createdBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> memberIds;
    private List<String> customerIds;
    private List<String> docIds;

    public Task(String description, String taskName, Priority priority, String createdBy) {
        this.description = description;
        this.taskName = taskName;
        this.priority = priority;
        this.progress = Progress.READY;
        this.createdDate = LocalDate.now();
        this.createdBy = createdBy;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
    }

    public Task(String description, String taskName, Priority priority, Progress progress, LocalDate startDate, LocalDate endDate) {
        this.description = description;
        this.taskName = taskName;
        this.priority = priority;
        this.progress = progress;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
