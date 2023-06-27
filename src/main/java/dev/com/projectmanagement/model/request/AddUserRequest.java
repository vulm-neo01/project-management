package dev.com.projectmanagement.model.request;

import dev.com.projectmanagement.model.stable.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {
    private String userId;
    private String projectId;
    private String taskId;
    private Role role;

    public AddUserRequest(String userId, String projectId, Role role) {
        this.userId = userId;
        this.projectId = projectId;
        this.role = role;
    }

    public AddUserRequest(String userId, String taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }
}
