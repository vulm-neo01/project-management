package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Notification;
import dev.com.projectmanagement.model.request.NotificationRequest;
import dev.com.projectmanagement.model.stable.NotiState;

import java.util.List;
import java.util.Optional;

public interface NotifyService {

    Notification sendInvite(Notification notification);

    Optional<NotiState> receiveInvite(NotificationRequest request);

    List<Optional<Notification>> getAllUnread();
}
