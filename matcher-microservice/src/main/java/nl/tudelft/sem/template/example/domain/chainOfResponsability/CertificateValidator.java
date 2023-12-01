package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Certificate;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.TimeSlot;

import java.util.List;

public class CertificateValidator extends BaseValidator {

    @Override
    public boolean handle(Activity activity, String position, Participant participant, List<TimeSlot> timeslots) {
        if (!position.equals("cox"))
            return super.checkNext(activity, position, participant, timeslots);
        Certificate boatCertificate = new Certificate(activity.getBoat());
        if (boatCertificate.isValid() && participant.getCertificate().isBetterCertificate(boatCertificate))
            return super.checkNext(activity, position, participant, timeslots);
        return false;
    }


}
