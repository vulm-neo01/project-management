package dev.com.projectmanagement.model;

import dev.com.projectmanagement.model.stable.NotiState;
import dev.com.projectmanagement.model.stable.NotificationType;
import dev.com.projectmanagement.model.stable.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @Generated
    private String notificationId;
    private NotificationType type;
    private String projectId;
    private String taskId;
    private String noteId;
    private String inviterId;
    private String inviterName;
    private Role role;
    private String email;
    private NotiState state;
    private String userId;

    public Notification(NotiState state) {
        this.state = state;
    }
}
