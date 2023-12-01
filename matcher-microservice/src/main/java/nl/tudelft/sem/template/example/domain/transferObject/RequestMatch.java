package nl.tudelft.sem.template.example.domain.transferObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.tudelft.sem.template.example.domain.Participant;

import java.util.List;
@NoArgsConstructor
@Setter
@Getter
public class RequestMatch {
    Participant participant;
    List<String> timeSlots;

    /**
     * Constructor for RequestMatch.
     * @param participant
     * @param timeSlots
     */
    public RequestMatch(Participant participant, List<String> timeSlots) {
        this.participant = participant;
        this.timeSlots = timeSlots;
    }
}
