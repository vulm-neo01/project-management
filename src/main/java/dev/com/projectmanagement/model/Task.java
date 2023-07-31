package dev.com.projectmanagement.model;

import dev.com.projectmanagement.model.stable.Priority;
import dev.com.projectmanagement.model.stable.Progress;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private String createdId;
    private String projectId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> memberIds;
    private List<String> docIds;
    private List<String> discussionIds;

    public Task(String description, String taskName, Priority priority, String createdId, String projectId) {
        this.description = description;
        this.taskName = taskName;
        this.createdId = createdId;
        this.projectId = projectId;
        this.priority = priority;
        this.progress = Progress.READY;
        this.createdDate = LocalDate.now();
        this.createdBy = String.valueOf(SecurityContextHolder.getContext().getAuthentication());
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

    public List<String> getMemberIds() {
        if (this.memberIds == null) {
            this.memberIds = new ArrayList<>();
        }
        return this.memberIds;
    }

    public List<String> getDocIds() {
        if (this.docIds == null) {
            this.docIds = new ArrayList<>();
        }
        return this.docIds;
    }

    public List<String> getDiscussionIds() {
        if (this.discussionIds == null) {
            this.discussionIds = new ArrayList<>();
        }
        return this.discussionIds;
    }
}
