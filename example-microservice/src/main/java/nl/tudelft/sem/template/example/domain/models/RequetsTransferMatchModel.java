package nl.tudelft.sem.template.example.domain.models;

import lombok.Data;

@Data
public class RequetsTransferMatchModel {
    Long activityId;
    String position;
    String timeSlot;
    String netId;
    String owner;

    public RequetsTransferMatchModel(Long l, String position, String s, String netId, String owner) {
        this.activityId=l;
        this.position=position;
        this.timeSlot=s;
        this.netId=netId;
        this.owner=owner;
    }
}
