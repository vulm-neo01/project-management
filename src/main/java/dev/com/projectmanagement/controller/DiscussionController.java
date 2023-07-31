package dev.com.projectmanagement.controller;

import dev.com.projectmanagement.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discussion")
@RequiredArgsConstructor
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;


}
