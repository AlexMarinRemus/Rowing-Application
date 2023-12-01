package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.TimeSlot;

import java.util.List;

public class PositionValidator extends BaseValidator {
    @Override
    public boolean handle(Activity activity, String position, Participant participant, List<TimeSlot> timeslots) {
        if (participant.getPositionManager().getPositions().contains(position))
            return super.checkNext(activity, position, participant, timeslots);

        return false;
    }

}
