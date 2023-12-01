package nl.tudelft.sem.template.example.domain.transferClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.tudelft.sem.template.example.domain.participant.Participant;

import java.util.List;
@NoArgsConstructor
@Setter
@Getter
public class RequestMatch {
    Participant participant;
    List<String> timeSlots;

    /**
     * Constructor for the request match.
     * @param participant
     * @param timeSlots
     */
    public RequestMatch(Participant participant, List<String> timeSlots) {
        this.participant = participant;
        this.timeSlots = timeSlots;
    }
}
