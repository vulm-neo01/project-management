package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Discussion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends MongoRepository<Discussion, String> {
}
