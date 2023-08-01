package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.model.Discussion;
import dev.com.projectmanagement.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/discussions")
@RequiredArgsConstructor
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @GetMapping("/task/{id}")
    public ResponseEntity<Optional<List<Discussion>>> getTaskDiscussion(@PathVariable("id") String taskId){
        return ResponseEntity.ok(discussionService.getTaskDiscussion(taskId));
    }

    @PostMapping("/task")
    public ResponseEntity<Optional<Discussion>> postDiscussionTask(@RequestBody Discussion discussion){
        return ResponseEntity.ok(discussionService.postDiscussionTask(discussion));
    }

}
