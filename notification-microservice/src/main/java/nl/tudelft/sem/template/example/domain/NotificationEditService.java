package nl.tudelft.sem.template.example.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationEditService {
    private final transient NotificationRepository notificationRepository;

    /**
     * Constructor for a notification service
     * @param notificationRepository
     */
    public NotificationEditService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    /**
     * Creates a notification.
     * @param activityId
     * @param netId
     * @param ownerId
     * @param message
     * @param ownerNotification
     * @return
     */
    public Notification createNotification(ActivityId activityId, NetId netId, NetId ownerId, String message, boolean ownerNotification){
        Notification n = new Notification(activityId, netId, ownerId, message, ownerNotification);
        return notificationRepository.save(n);
    }

    /**
     * Adds a notification to the database.
     * @param notification
     * @return
     */
    public Notification addNotification(Notification notification){
        return notificationRepository.save(notification);
    }


    /**
     * Deletes a notification.
     * @param n
     */
    public void deleteNotification(Notification n) {
        notificationRepository.delete(n);
    }
}
