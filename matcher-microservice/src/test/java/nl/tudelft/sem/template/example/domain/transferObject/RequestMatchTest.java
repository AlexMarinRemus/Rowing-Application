package nl.tudelft.sem.template.example.domain.transferObject;

import nl.tudelft.sem.template.example.domain.Participant;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RequestMatchTest {

    @Test
    void getTransferMatch() {
        Participant participant = Mockito.mock(Participant.class);

        RequestMatch requestMatch = new RequestMatch(participant,new ArrayList<>());
        assertNotNull(requestMatch);
        assertNotNull(new RequestMatch());
    }
    @Test
    void getterTesting() {
        Participant participant = Mockito.mock(Participant.class);

        RequestMatch requestMatch = new RequestMatch(participant,new ArrayList<>());
        assertEquals(participant,requestMatch.getParticipant());
        assertEquals(new ArrayList<>(),requestMatch.getTimeSlots());
    }
    @Test
    void setterTesting() {
        Participant participant = Mockito.mock(Participant.class);

        RequestMatch requestMatch = new RequestMatch();
        requestMatch.setParticipant(participant);
        requestMatch.setTimeSlots(new ArrayList<>());
        assertEquals(participant,requestMatch.getParticipant());
        assertEquals(new ArrayList<>(),requestMatch.getTimeSlots());
    }

}