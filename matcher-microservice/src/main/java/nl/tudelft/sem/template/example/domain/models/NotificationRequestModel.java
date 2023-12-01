package nl.tudelft.sem.template.example.domain.models;

import lombok.Data;

@Data
public class NotificationRequestModel {
    private String activityId;
    private String netId;
    private String message;


    public String getNetId(){
        return this.netId;
    }


}
