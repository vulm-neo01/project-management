package dev.com.projectmanagement.service.impl;

import dev.com.projectmanagement.repository.DiscussionRepository;
import dev.com.projectmanagement.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscussionImpl implements DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

}
