package nl.tudelft.sem.template.example.domain.participant;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositionManagerTest {

    @Test
    void getPositions() {
        PositionManager pm= new PositionManager("cox,coach");
        List<String> positions= pm.getPositions();
        assertTrue(positions.contains("cox"));
        assertTrue(positions.contains("coach"));
    }

    @Test
    void testToString() {
        String s= "cox,coach";
        PositionManager pm= new PositionManager(s);
        assertTrue(pm.toString().equals(s));
    }
}