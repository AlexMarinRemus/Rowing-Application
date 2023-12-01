package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.TimeSlot;
import nl.tudelft.sem.template.example.domain.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotValidatorTest {
    TimeSlotValidator timeSlotValidator;
    SimpleDateFormat converter;
    @BeforeEach
    void setup(){
        timeSlotValidator = new TimeSlotValidator();
        converter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        converter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    @Test
    void handleSmallerParticipantStart() {
        Participant participant= Mockito.mock(Participant.class);
        Training activity = Mockito.mock(Training.class);
        Mockito.when(activity.getTimeSlot()).thenReturn(new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        List<TimeSlot> timeSlotList = List.of(new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        assertFalse(timeSlotValidator.handle(activity,"cox",participant,timeSlotList));
    }
    @Test
    void handleEqualParticipantStart() {
        Participant participant= Mockito.mock(Participant.class);
        Training activity = Mockito.mock(Training.class);
        Mockito.when(activity.getTimeSlot()).thenReturn(new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        List<TimeSlot> timeSlotList = List.of(new TimeSlot("21-12-2012 17:03;29-12-2022 15:22"));
        assertTrue(timeSlotValidator.handle(activity,"cox",participant,timeSlotList));
    }
    @Test
    void handleGreaterParticipantStart() {
        Participant participant= Mockito.mock(Participant.class);
        Training activity = Mockito.mock(Training.class);
        Mockito.when(activity.getTimeSlot()).thenReturn(new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        List<TimeSlot> timeSlotList = List.of(new TimeSlot("21-12-2012 17:00;29-12-2022 15:22"));
        assertTrue(timeSlotValidator.handle(activity,"cox",participant,timeSlotList));
    }
    @Test
    void handleGreaterParticipantEnd() {
        Participant participant= Mockito.mock(Participant.class);
        Training activity = Mockito.mock(Training.class);
        Mockito.when(activity.getTimeSlot()).thenReturn(new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        List<TimeSlot> timeSlotList = List.of(new TimeSlot("21-12-2012 17:00;29-12-2022 15:25"));
        assertTrue(timeSlotValidator.handle(activity,"cox",participant,timeSlotList));
    }
    @Test
    void handleSmallerParticipantEnd() {
        Participant participant= Mockito.mock(Participant.class);
        Training activity = Mockito.mock(Training.class);
        Mockito.when(activity.getTimeSlot()).thenReturn(new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        List<TimeSlot> timeSlotList = List.of(new TimeSlot("21-12-2012 17:00;29-12-2022 15:21"));
        assertFalse(timeSlotValidator.handle(activity,"cox",participant,timeSlotList));
    }

    @Test
    void adjustStartTimeCompetition() throws ParseException {

        Date  before = converter.parse("21-12-2012 17:30");
        Date  after = converter.parse("20-12-2012 17:30");

        assertEquals(timeSlotValidator.adjustStartTime(before,true),after);
    }
    @Test
    void adjustStartTimeTraining() throws ParseException {

        Date  before = converter.parse("21-12-2012 17:30");
        Date  after = converter.parse("21-12-2012 17:00");

        assertEquals(timeSlotValidator.adjustStartTime(before,false),after);

    }
}