package dev.com.projectmanagement.service;

import dev.com.projectmanagement.model.Discussion;

import java.util.List;
import java.util.Optional;

public interface DiscussionService {
    Optional<List<Discussion>> getTaskDiscussion(String taskId);

    Optional<Discussion> postDiscussionTask(Discussion discussion);
}
