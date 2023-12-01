package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.TimeSlot;

import java.util.List;

public abstract class BaseValidator implements Validator {
    private transient Validator next;

    public void setNext(Validator h) {
        this.next = h;
    }

    /**
     * Checks if the next validator is null.
     * @param activity
     * @param participant
     * @param timeslots
     * @return
     */
    protected boolean checkNext(Activity activity, String position, Participant participant, List<TimeSlot> timeslots) {
        if (next == null) {
            return true;
        }
        return next.handle(activity, position, participant, timeslots);
    }
    public Validator getNext() {
        return next;
    }
}
