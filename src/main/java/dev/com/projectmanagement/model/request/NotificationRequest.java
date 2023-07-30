package dev.com.projectmanagement.model.request;

import dev.com.projectmanagement.model.stable.NotiState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String notificationId;
    private NotiState state;
}
