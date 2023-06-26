package dev.com.projectmanagement.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @Generated
    private String projectId;
    private String description;
    private String projectName;
    private Progress progress;
    private LocalDate createdDate;
    private String createdBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> managerIds;
    private List<String> memberIds;
    private List<String> customerIds;
    private List<String> taskIds;
    private List<String> docIds;

    public Project(String description, String projectName, String createdBy) {
        this.description = description;
        this.projectName = projectName;
        this.progress = Progress.READY;
        this.createdDate = LocalDate.now();
        this.createdBy = createdBy;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
    }

    public Project(String description, String projectName, Progress progress, LocalDate startDate, LocalDate endDate) {
        this.description = description;
        this.projectName = projectName;
        this.progress = progress;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
