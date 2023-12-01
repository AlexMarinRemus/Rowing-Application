package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
    @Test
    void setIdTest(){
        Training training = new Training(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"));
        training.setId(1L);
        assertEquals(training.getId(),1L);
    }
    @Test
    void setTimeSlot(){
        Training training = new Training(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"));
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");
        training.setTimeSlot(timeSlot);
        assertEquals(training.getTimeSlot(),timeSlot);
    }

    @Test
    void toStringActivity(){
        Training training = new Training(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"));
        assertTrue(training.toString().equals("Activity{, owner=owner, timeslot=21-12-2012 17:33;29-12-2022 15:22, boat='C4', positions=[cox]}"));
    }


}