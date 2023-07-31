package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Notification;
import dev.com.projectmanagement.model.Project;
import dev.com.projectmanagement.model.User;
import dev.com.projectmanagement.model.request.NotificationRequest;
import dev.com.projectmanagement.model.request.TaskRequest;
import dev.com.projectmanagement.model.stable.NotiState;
import dev.com.projectmanagement.model.stable.NotificationType;
import dev.com.projectmanagement.repository.NotificationRepository;
import dev.com.projectmanagement.repository.ProjectRepository;
import dev.com.projectmanagement.repository.TaskRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class NotifyImpl implements NotifyService {

    private static final Logger logger = LoggerFactory.getLogger(NotifyImpl.class);
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Notification sendInvite(Notification notification) {
        notification.setState(NotiState.UNREAD);
        notification.setType(NotificationType.REPLY);
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<NotiState> receiveInviteProject(NotificationRequest request) {
        String notificationId = request.getNotificationId();
        NotiState state = request.getState();
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        Notification updatedNotification = notification.orElseThrow(() -> new RuntimeException("Notification not found"));
        updatedNotification.setState(state);
        notificationRepository.save(updatedNotification);

        String projectId = updatedNotification.getProjectId();
        String userEmail = updatedNotification.getEmail();
        Optional<Project> updatedProject = projectRepository.findById(projectId);
        Optional<User> updatedUser = userRepository.findByEmail(userEmail);

        if(state == NotiState.ACCEPT && updatedUser.isPresent() && updatedProject.isPresent()){
            Project project = updatedProject.get();
            User user = updatedUser.get();

            project.getUsers().put(user.getUserId(), updatedNotification.getRole());
            user.getProjectIds().add(projectId);

            projectRepository.save(project);
            userRepository.save(user);
        }
        return Optional.ofNullable(state);
    }

    @Override
    public List<Optional<Notification>> getAllUnread() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<User> user = userRepository.findByEmail(email);
        User respondUser = user.orElseThrow(() -> new UsernameNotFoundException("Not find Email"));

        List<Optional<Notification>> notificationList = notificationRepository.findAllByEmail(email);

        List<Optional<Notification>> unreadNotification = notificationList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(notification -> notification.getState().equals(NotiState.UNREAD))
                .map(Optional::of)
                .collect(Collectors.toList());
        return unreadNotification;
    }

    @Override
    public Optional<Notification> receiveInviteTask(NotificationRequest request) {
        String notificationId = request.getNotificationId();
        logger.info(request.toString());
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        Notification updatedNotification = notification.orElseThrow(() -> new RuntimeException("Notification not found"));
        updatedNotification.setState(NotiState.READ);
        notificationRepository.save(updatedNotification);

        return Optional.of(updatedNotification);
    }
}
