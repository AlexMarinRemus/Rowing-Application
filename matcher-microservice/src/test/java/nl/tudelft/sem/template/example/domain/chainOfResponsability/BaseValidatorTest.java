package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.Training;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.Time;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.mail.Part;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BaseValidatorTest {


    @Test
    void checkNext() {
        TimeSlotValidator timeSlotValidator = new TimeSlotValidator();
        Activity activity = Mockito.mock(Activity.class);
        Participant participant = Mockito.mock(Participant.class);
        CertificateValidator certificateValidator = Mockito.mock(CertificateValidator.class);
        Mockito.when(certificateValidator
                .handle(activity,"cox",participant,new ArrayList<>())).thenReturn(true);
        timeSlotValidator.setNext(certificateValidator);

        assertTrue(timeSlotValidator.checkNext(activity,"cox",participant,new ArrayList<>()));
    }
    @Test
    void checkNextNull() {
        TimeSlotValidator timeSlotValidator = new TimeSlotValidator();
        Activity activity = Mockito.mock(Activity.class);
        Participant participant = Mockito.mock(Participant.class);
        CertificateValidator certificateValidator = Mockito.mock(CertificateValidator.class);


        assertTrue(timeSlotValidator.checkNext(activity,"cox",participant,new ArrayList<>()));
    }
}