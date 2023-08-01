package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Discussion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscussionRepository extends MongoRepository<Discussion, String> {

    Optional<List<Discussion>> findAllByRootId(String rootId);
}
