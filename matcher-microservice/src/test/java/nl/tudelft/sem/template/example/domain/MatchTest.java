package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    void getId() {

        Match match = new Match("netId",1L,"cox");
        match.setId(1);
        assertEquals(match.getId(),1);
    }

    @Test
    void getNetId() {
        Match match = new Match("netId",1L,"cox");
        assertEquals(match.getNetId(),"netId");
    }

    @Test
    void getActivityId() {
        Match match = new Match("netId",1L,"cox");
        assertEquals(match.getActivityId(),1L);
    }

    @Test
    void getPosition() {
        Match match = new Match("netId",1L,"cox");
        assertEquals(match.getPosition(),"cox");
    }
    @Test
    void noArgConstructor() {
        Match match1 = new Match();

        assertNotNull(match1);
    }

}