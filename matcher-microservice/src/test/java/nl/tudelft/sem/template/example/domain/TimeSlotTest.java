package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeSlotTest {
    @Test
    public void constructorsTest(){
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");
        assertNotNull(timeSlot);
        assertNotNull(new TimeSlot());
    }
    @Test
    public void getTimeSlot(){
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");


       assertEquals(timeSlot,TimeSlot.getTimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
    }
    @Test
    public void getTimeSlots(){
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");
        List<String> timeStrings = List.of("21-12-2012 17:33;29-12-2022 15:22");

        assertEquals(List.of(timeSlot),TimeSlot.getTimeSlots(timeStrings));
    }
    @Test
    public void toStringTest(){
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");
        assertEquals(timeSlot.toString(),"21-12-2012 17:33;29-12-2022 15:22");
    }

    @Test
    public void getMultipleTimeSlots() {
        List<String> timeSlots = List.of("10-10-2022 14:00;10-10-2022 16:00", "22-01-2023 14:00;22-01-2023 16:00");
        assertEquals(2, TimeSlot.getTimeSlots(timeSlots).size());
        assertEquals("10-10-2022 14:00;10-10-2022 16:00", TimeSlot.getTimeSlots(timeSlots).get(0).toString());
        assertEquals("22-01-2023 14:00;22-01-2023 16:00", TimeSlot.getTimeSlots(timeSlots).get(1).toString());
    }

    @Test
    public void TestSet() throws ParseException {
        SimpleDateFormat converter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        converter.setTimeZone(TimeZone.getTimeZone("UTC"));
        TimeSlot timeSlot = new TimeSlot("21-12-2012 17:33;29-12-2022 15:22");
        timeSlot.setBegin(converter.parse("10-10-2022 14:00"));
    }



}