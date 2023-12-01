package nl.tudelft.sem.template.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    /**
     * Find user by NetID.
     */
    Optional<Notification> findByActivityId(ActivityId activityId);
    /**
     * Check if an existing user already uses a NetID.
     */
    boolean existsByActivityId(ActivityId activityId);

    List<Notification> getAllByNetId(NetId netId);

    List<Notification> getAllByOwnerId(NetId ownerId);

    List<Notification> getAllByActivityId(ActivityId activityId);
}
