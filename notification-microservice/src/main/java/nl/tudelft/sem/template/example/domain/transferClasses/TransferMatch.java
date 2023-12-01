package nl.tudelft.sem.template.example.domain.transferClasses;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferMatch {
    Long activityId;
    String position;
    String timeSlot;
    String netId;
    String owner;
    public TransferMatch(Long activityId, String position, String timeSlot, String netId, String owner) {
        this.activityId = activityId;
        this.position = position;
        this.timeSlot = timeSlot;
        this.netId = netId;
        this.owner=owner;
    }
}
