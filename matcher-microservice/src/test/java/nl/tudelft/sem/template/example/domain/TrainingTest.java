package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {
    @Test
    void constructorTest(){
        Training training = new Training(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"));
        assertNotNull(training);
        assertNotNull(new Training());
    }
    @Test
    void equals(){
        Training training = new Training(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"));
        assertEquals(training,training);
    }



}