package nl.tudelft.sem.template.example.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequestModel {
    private String activityId;
    private String netId;
    private String ownerId;
    private String message;

    private boolean ownerNotification;

    public String getActivityId(){
        return this.activityId;
    }

    public String getNetId(){
        return this.netId;
    }
    public String getOwnerId(){return this.ownerId;}

    public String getMessage(){
        return this.message;
    }

    public boolean isOwnerNotification(){return this.ownerNotification;}

}
