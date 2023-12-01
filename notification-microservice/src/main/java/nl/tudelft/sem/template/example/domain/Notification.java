package nl.tudelft.sem.template.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "notification", sequenceName = "notification", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "notification")
    private int id;
    @Column(name = "activityId")
    @Convert(converter = ActivityIdConverter.class)
    private ActivityId activityId;
    @Column(name="netId")
    @Convert(converter = NetIdConverter.class)
    private NetId netId;
    @Column
    @Convert(converter = NetIdConverter.class)
    private NetId ownerId;
    @Column(name="message")
    private String message;
    @Column(name="ownerNotification")
    private boolean ownerNotification;
    @Column(name="position")
    private String position;
    @Column(name="timeSlot")
    private String timeSlot;

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notif = (Notification) o;
        return id == (notif.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(activityId);
    }
    public Notification(ActivityId activityId, NetId netId, NetId ownerId, String message, boolean ownerNotification){
        this.activityId = activityId;
        this.netId = netId;
        this.ownerId = ownerId;
        this.message = message;
        this.ownerNotification = ownerNotification;
    }

    /**
     * Constructor for a notification.
     * @param activityId
     * @param netId
     * @param ownerId
     * @param message
     * @param ownerNotification
     * @param position
     * @param timeSlot
     */
    public Notification(ActivityId activityId, NetId netId, NetId ownerId, String message, boolean ownerNotification, String position, String timeSlot){
        this.activityId = activityId;
        this.netId = netId;
        this.ownerId = ownerId;
        this.message = message;
        this.ownerNotification = ownerNotification;
        this.position = position;
        this.timeSlot = timeSlot;
    }
}
