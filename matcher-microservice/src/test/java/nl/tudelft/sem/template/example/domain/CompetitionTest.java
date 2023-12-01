package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionTest {
    @Test
    void testConstructor(){
        Competition competition = new Competition(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"),"org","M",true);
        assertNotNull(competition);

    }
    @Test
    void getCompetitive(){
        Competition competition = new Competition(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"),"org","M",true);
        assertTrue(competition.getCompetitive());

    }
    @Test
    void getCompetitiveFalse(){
        Competition competition = new Competition(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"),"org","M",false);
        assertFalse(competition.getCompetitive());

    }
    @Test
    void constructorTest(){
        assertNotNull(new Competition());
    }
    @Test
    void gettersTest(){
        Competition competition = new Competition(new NetId("owner"),
                new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"),
                "C4", List.of("cox"),"org","M",true);
        assertEquals(competition.getCompetitive(),true);
        assertEquals(competition.getGender(),"M");
        assertEquals(competition.getTimeSlot(),new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        assertEquals(competition.getOwner(),new NetId("owner"));
        assertEquals(competition.getBoat(),"C4");
        assertEquals(competition.getOrganization(),"org");
        assertEquals(competition.getPositions(),List.of("cox"));


    }
    @Test
    void setterTest(){
        Competition competition = new Competition();
        competition.setCompetitive(true);
        competition.setGender("M");
        competition.setTimeSlot(new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        competition.setOwner(new NetId("owner"));
        competition.setPositions( List.of("cox"));
        competition.setOrganization("org");
        competition.setBoat("C4");
        assertEquals(competition.getCompetitive(),true);
        assertEquals(competition.getGender(),"M");
        assertEquals(competition.getTimeSlot(),new TimeSlot("21-12-2012 17:33;29-12-2022 15:22"));
        assertEquals(competition.getOwner(),new NetId("owner"));
        assertEquals(competition.getBoat(),"C4");
        assertEquals(competition.getOrganization(),"org");
        assertEquals(competition.getPositions(),List.of("cox"));


    }


}