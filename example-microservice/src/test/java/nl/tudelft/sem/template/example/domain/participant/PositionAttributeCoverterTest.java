package nl.tudelft.sem.template.example.domain.participant;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositionAttributeCoverterTest {

    @Test
    void convertToDatabaseColumn() {
        PositionAttributeCoverter pac= new PositionAttributeCoverter();
        List<String> pos = Arrays.asList("cox", "coach", "port side rower", "starboard side rower", "sculling rower");
        int min=0;
        int max=4;
        int random1 = (int)Math.floor(Math.random()*(max-min+1)+min);
        int random2 = (int)Math.floor(Math.random()*(max-min+1)+min);
        String positions=pos.get(random1)+","+pos.get(random2);
        PositionManager pm = new PositionManager(positions);
        assertTrue(pac.convertToDatabaseColumn(pm).equals(pm.toString()));
    }

    @Test
    void convertToEntityAttribute() {
        PositionAttributeCoverter pac= new PositionAttributeCoverter();
        List<String> pos = Arrays.asList("cox", "coach", "port side rower", "starboard side rower", "sculling rower");
        int min=0;
        int max=4;
        int random1 = (int)Math.floor(Math.random()*(max-min+1)+min);
        int random2 = (int)Math.floor(Math.random()*(max-min+1)+min);
        String positions=pos.get(random1)+","+pos.get(random2);
        PositionManager pm = new PositionManager(positions);
        assertTrue(pac.convertToEntityAttribute(positions).equals(pm));

    }
}