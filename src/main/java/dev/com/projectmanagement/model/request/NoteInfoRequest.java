package dev.com.projectmanagement.model.request;

import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteInfoRequest {
    private String noteId;
    private String title;
    private LocalDateTime alertTime;
    private String projectId;
    private String taskId;
}
