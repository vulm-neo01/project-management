package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Notification;
import dev.com.projectmanagement.model.request.NotificationRequest;
import dev.com.projectmanagement.model.request.TaskRequest;
import dev.com.projectmanagement.model.stable.NotiState;
import dev.com.projectmanagement.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private NotifyService notifyService;

    @PostMapping("/invite")
    public ResponseEntity<Notification> sendInvite(@RequestBody Notification notification){
        return ResponseEntity.ok(notifyService.sendInvite(notification));
    }

    @GetMapping
    public ResponseEntity<List<Optional<Notification>>> getAllUnread(){
        return ResponseEntity.ok(notifyService.getAllUnread());
    }

    @PostMapping("/replyProject")
    public ResponseEntity<Optional<NotiState>> replyInviteProject(@RequestBody NotificationRequest request){
        return ResponseEntity.ok(notifyService.receiveInviteProject(request));
    }

    @PostMapping("/reply")
    public ResponseEntity<Optional<Notification>> replyInviteTask(@RequestBody NotificationRequest request){
        return ResponseEntity.ok(notifyService.receiveInviteTask(request));
    }
}
