package dev.com.projectmanagement.repository;

import dev.com.projectmanagement.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Optional<Notification>> findAllByEmail(String email);

    Optional<Notification> findByNoteId(String noteId);
}
