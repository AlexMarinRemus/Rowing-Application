package nl.tudelft.sem.template.example.controllers;


import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.domain.NetId;
import nl.tudelft.sem.template.example.domain.Notification;
import nl.tudelft.sem.template.example.domain.NotificationEditService;
import nl.tudelft.sem.template.example.domain.NotificationGetService;
import nl.tudelft.sem.template.example.domain.factories.OwnerNotificationParserFactory;
import nl.tudelft.sem.template.example.domain.factories.ParserFactory;
import nl.tudelft.sem.template.example.domain.factories.ParticipantNotificationParserFactory;
import nl.tudelft.sem.template.example.domain.transferClasses.TransferMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("notifications")
public class NotificationController {
    private final transient NotificationEditService notificationEditService;

    private final transient NotificationGetService notificationGetService;
    private final transient AuthManager authManager;

    private final transient ParserFactory ownerNotificationParserFactory;

    private final transient ParserFactory participantNotificationParserFactory;

    /**
     * Constructor for NotificationController.
     * @param notificationEditService
     * @param authManager
     */
    @Autowired
    public NotificationController(NotificationEditService notificationEditService, NotificationGetService notificationGetService,
                                  AuthManager authManager){
        this.notificationEditService = notificationEditService;
        this.notificationGetService = notificationGetService;
        this.authManager = authManager;
        this.ownerNotificationParserFactory = new OwnerNotificationParserFactory();
        this.participantNotificationParserFactory = new ParticipantNotificationParserFactory();
    }

    /**
     * Get all notifications.
     * @return List of notifications
     */
    @GetMapping("/findAll")
    public List<Notification> findAllNotifications(){
        return notificationGetService.getAllNotifications();
    }

    /**
     * Create participant notification.
     * @param requests
     * @return
     */
    @PostMapping("/createParticipantNotification")
    public ResponseEntity<List<Notification>> createParticipantNotification(@RequestBody List<TransferMatch> requests){
        List<Notification> result = new ArrayList<>();
        for (TransferMatch request : requests){
            Notification temp = participantNotificationParserFactory.createParser().parseOtherWay(request);
            notificationEditService.addNotification(temp);
            result.add(temp);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Create owner notification.
     * @param request
     * @return
     */
    @PostMapping("/createOwnerNotification")
    public ResponseEntity<Notification> createOwnerNotification (@RequestBody TransferMatch request){
        Notification temp = ownerNotificationParserFactory.createParser().parseOtherWay(request);
        notificationEditService.addNotification(temp);
        return ResponseEntity.ok(temp);
    }

    /**
     * Get all notifications for the owner.
     * @return
     */
    @GetMapping("/getOwnerNotifications")
    public ResponseEntity<List<TransferMatch>> getOwnerNotifications(){
        List<Notification> notifications = notificationGetService.getOwnerNotifications((new NetId(authManager.getNetId())));
        List<TransferMatch> result = new ArrayList<>();
        for (Notification n : notifications){
            if (n.isOwnerNotification()) {
                result.add(ownerNotificationParserFactory.createParser().parse(n));
                notificationEditService.deleteNotification(n);
            }
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Get all notifications for the participant.
     * @return
     */
    @GetMapping("/getParticipantNotifications")
    public ResponseEntity<List<TransferMatch>> getParticipantNotifications(){
        List<Notification> notifications = notificationGetService.getUserNotifications(new NetId(authManager.getNetId()));
        List<TransferMatch> result = new ArrayList<>();
        for (Notification n : notifications){
            if (!n.isOwnerNotification()) {
                result.add(participantNotificationParserFactory.createParser().parse(n));
                notificationEditService.deleteNotification(n);
            }
        }

        return ResponseEntity.ok(result);
    }

}
