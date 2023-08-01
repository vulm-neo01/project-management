package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.model.Discussion;
import dev.com.projectmanagement.repository.DiscussionRepository;
import dev.com.projectmanagement.repository.TaskRepository;
import dev.com.projectmanagement.repository.UserRepository;
import dev.com.projectmanagement.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscussionImpl implements DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<List<Discussion>> getTaskDiscussion(String taskId) {
        Optional<List<Discussion>> discussionList = discussionRepository.findAllByRootId(taskId);
        return discussionList;
    }

    @Override
    public Optional<Discussion> postDiscussionTask(Discussion discussion) {
        discussion.setTime(LocalDateTime.now());
        return Optional.of(discussionRepository.save(discussion));
    }
}
