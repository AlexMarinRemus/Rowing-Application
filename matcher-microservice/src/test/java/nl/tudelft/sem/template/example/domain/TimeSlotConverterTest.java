package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotConverterTest {
    TimeSlotConverter timeSlotConverter;
    @BeforeEach
    void setup(){
        timeSlotConverter = new TimeSlotConverter();
    }
    @Test
    public void convertToDatabaseColumn(){
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");
        assertEquals(timeSlotConverter.convertToDatabaseColumn(timeSlot),
                "21-12-2012 17:33;29-12-2022 15:22"
                );

    }
    @Test
    public void convertToTimeSlot(){
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");
        assertEquals(timeSlotConverter.convertToEntityAttribute("21-12-2012 17:33;29-12-2022 15:22"),
                timeSlot
        );

    }

}