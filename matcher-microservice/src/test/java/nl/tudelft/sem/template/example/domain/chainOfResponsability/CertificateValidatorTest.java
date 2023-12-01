package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Certificate;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.PositionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CertificateValidatorTest {
    CertificateValidator certificateValidator;
    @BeforeEach
    void setup(){
        certificateValidator = new CertificateValidator();
    }
    @Test
    void handleNeutralPosition() {

        Participant participant= Mockito.mock(Participant.class);
        Activity activity = Mockito.mock(Activity.class);

        assertTrue(certificateValidator.handle(activity,"coach",participant,new ArrayList<>()));
    }
    @Test
    void handleInvalidBoatPosition() {

        Participant participant= Mockito.mock(Participant.class);
        Activity activity = Mockito.mock(Activity.class);
        Mockito.when(activity.getBoat()).thenReturn("invalidBoat");

        assertFalse(certificateValidator.handle(activity,"cox",participant,new ArrayList<>()));
    }
    @Test
    void handleWeakerCertificate() {


        Activity activity = Mockito.mock(Activity.class);
        Mockito.when(activity.getBoat()).thenReturn("8+");
        Certificate certificate = Mockito.mock(Certificate.class);
        Mockito.when(certificate.isBetterCertificate(any(Certificate.class))).thenReturn(false);
        Participant participant = new Participant();
        participant.setCertificate(certificate);

        assertFalse(certificateValidator.handle(activity,"cox",participant,new ArrayList<>()));

    }
    @Test
    void handleEqualCertificate() {


        Activity activity = Mockito.mock(Activity.class);
        Mockito.when(activity.getBoat()).thenReturn("8+");
        Certificate certificate = Mockito.mock(Certificate.class);
        Mockito.when(certificate.isBetterCertificate(any(Certificate.class))).thenReturn(true);
        Participant participant = new Participant();
        participant.setCertificate(certificate);

        assertTrue(certificateValidator.handle(activity,"cox",participant,new ArrayList<>()));

    }


}