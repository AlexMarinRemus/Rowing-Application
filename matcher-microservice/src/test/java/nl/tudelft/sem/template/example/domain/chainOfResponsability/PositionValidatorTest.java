package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.PositionManager;
import org.hibernate.dialect.function.PositionSubstringFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PositionValidatorTest {
    PositionValidator positionValidator;
    @BeforeEach
    void setup(){
         positionValidator = new PositionValidator();
    }


    @Test
    void handleTrue() {
        PositionManager positionManager = new PositionManager("cox");
        Participant participant= Mockito.mock(Participant.class);
        Mockito.when(participant.getPositionManager()).thenReturn(positionManager);
        Activity activity = Mockito.mock(Activity.class);

        assertTrue(positionValidator.handle(activity,"cox",participant,new ArrayList<>()));
    }
    @Test
    void handleFalse() {
        PositionManager positionManager = new PositionManager("coach");
        Participant participant= Mockito.mock(Participant.class);
        Mockito.when(participant.getPositionManager()).thenReturn(positionManager);
        Activity activity = Mockito.mock(Activity.class);

        assertFalse(positionValidator.handle(activity,"cox",participant,new ArrayList<>()));
    }
    @Test
    void handleMultiple() {
        PositionManager positionManager = new PositionManager("coach,cox,sculling rower");
        Participant participant= Mockito.mock(Participant.class);
        Mockito.when(participant.getPositionManager()).thenReturn(positionManager);
        Activity activity = Mockito.mock(Activity.class);

        assertTrue(positionValidator.handle(activity,"cox",participant,new ArrayList<>()));
    }
}