package nl.tudelft.sem.template.example.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationGetService {
    private final transient NotificationRepository notificationRepository;

    /**
     * Constructor for a notification service
     * @param notificationRepository
     */
    public NotificationGetService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }


    /**
     * Gets all notifications.
     * @return
     */
    public List<Notification> getAllNotifications(){
        return notificationRepository.findAll();
    }

    /**
     * Gets all notifications for a specific user.
     * @param netId
     * @return
     */
    public List<Notification> getUserNotifications(NetId netId){
        return notificationRepository.getAllByNetId(netId);
    }

    /**
     * Gets all notifications for a specific user.
     * @param ownerId
     * @return
     */
    public List<Notification> getOwnerNotifications(NetId ownerId){
        return notificationRepository.getAllByOwnerId(ownerId);
    }
}
