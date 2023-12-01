package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.TimeSlot;

import java.util.List;

public interface Validator {
    void setNext(Validator handler);
    Validator getNext();

    boolean handle(Activity activity, String position, Participant participant, List<TimeSlot> timeslots);

}
