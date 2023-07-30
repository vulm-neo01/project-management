package dev.com.projectmanagement.model;

import dev.com.projectmanagement.model.stable.Role;
import dev.com.projectmanagement.model.stable.Progress;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Map<String, Role> users;
    private List<String> taskIds;
    private List<String> docIds;

    public Project(String description, String projectName, String createdBy) {
        this.description = description;
        this.projectName = projectName;
        this.progress = Progress.READY;
        this.createdDate = LocalDate.now();
        this.createdBy = String.valueOf(SecurityContextHolder.getContext().getAuthentication());
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

    public List<String> getTaskIds() {
        if (this.taskIds == null) {
            this.taskIds = new ArrayList<>();
        }
        return this.taskIds;
    }

    public List<String> getDocIds() {
        if (this.docIds == null) {
            this.docIds = new ArrayList<>();
        }
        return this.docIds;
    }
}
