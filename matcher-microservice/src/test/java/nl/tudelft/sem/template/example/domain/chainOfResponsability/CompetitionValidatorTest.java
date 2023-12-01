package nl.tudelft.sem.template.example.domain.chainOfResponsability;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Competition;
import nl.tudelft.sem.template.example.domain.Participant;
import nl.tudelft.sem.template.example.domain.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionValidatorTest {
    CompetitionValidator competitionValidator;
    @BeforeEach
    void setup(){
        competitionValidator = new CompetitionValidator();
    }
    @Test
    void handleTraining() {
        Participant participant= Mockito.mock(Participant.class);
        Training activity = Mockito.mock(Training.class);

        assertTrue(competitionValidator.handle(activity,"coach",participant,new ArrayList<>()));
    }
    @Test
    void handleCompetitionTrue() {
        Participant participant= Mockito.mock(Participant.class);
        Competition activity = Mockito.mock(Competition.class);
        Mockito.when(activity.getGender()).thenReturn("M");
        Mockito.when(participant.getGender()).thenReturn("M");
        Mockito.when(activity.getOrganization()).thenReturn("org");
        Mockito.when(participant.getOrganization()).thenReturn("org");
        Mockito.when(activity.getCompetitive()).thenReturn(true);
        Mockito.when(participant.getLevel()).thenReturn(true);

        assertTrue(competitionValidator.handle(activity,"coach",participant,new ArrayList<>()));
    }
    @Test
    void handleCompetitionDifferentGender() {
        Participant participant= Mockito.mock(Participant.class);
        Competition activity = Mockito.mock(Competition.class);
        Mockito.when(activity.getGender()).thenReturn("M");
        Mockito.when(participant.getGender()).thenReturn("F");

        assertFalse(competitionValidator.handle(activity,"coach",participant,new ArrayList<>()));
    }
    @Test
    void handleCompetitionDifferentOrganization() {
        Participant participant= Mockito.mock(Participant.class);
        Competition activity = Mockito.mock(Competition.class);
        Mockito.when(activity.getGender()).thenReturn("M");
        Mockito.when(participant.getGender()).thenReturn("M");
        Mockito.when(activity.getOrganization()).thenReturn("org1");
        Mockito.when(participant.getOrganization()).thenReturn("org2");
        assertFalse(competitionValidator.handle(activity,"coach",participant,new ArrayList<>()));
    }
    @Test
    void handleCompetitionDifferentLevel() {
        Participant participant= Mockito.mock(Participant.class);
        Competition activity = Mockito.mock(Competition.class);
        Mockito.when(activity.getGender()).thenReturn("M");
        Mockito.when(participant.getGender()).thenReturn("M");
        Mockito.when(activity.getOrganization()).thenReturn("org");
        Mockito.when(participant.getOrganization()).thenReturn("org");
        Mockito.when(activity.getCompetitive()).thenReturn(true);
        Mockito.when(participant.getLevel()).thenReturn(false);
        assertFalse(competitionValidator.handle(activity,"coach",participant,new ArrayList<>()));
    }
}