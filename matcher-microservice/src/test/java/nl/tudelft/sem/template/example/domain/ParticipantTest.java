package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {
    @Test
    void constructorTest(){
        Participant participant = new Participant(new NetId("participant"),
                new PositionManager("cox"),
                "M",new Certificate("C4"),"org",true);
        assertNotNull(participant);
        assertNotNull(new Participant());

    }


}