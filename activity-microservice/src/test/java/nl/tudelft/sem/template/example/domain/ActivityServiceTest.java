package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivityServiceTest {

    ActivityServiceGet serviceGet;
    ActivityServiceCreateDelete serviceCreateDelete;
    ActivityServiceEdit serviceEdit;
    ActivityRepository activityRepo;
    NetId user = new NetId("paula");
    Competition competition = new Competition(user, new TimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "boat", List.of("captain"), "organization", "female", true);
    Training training = new Training(new NetId("zosia"), new TimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "boat", List.of("captain", "cox"));

    @BeforeEach
    public void setUp() {
        activityRepo  = mock(ActivityRepository.class);
        serviceGet = new ActivityServiceGet(activityRepo);
        serviceCreateDelete = new ActivityServiceCreateDelete(activityRepo);
        serviceEdit = new ActivityServiceEdit(activityRepo);
    }

    public void setCompetitionRepo() {
        when(activityRepo.existsById(competition.getId())).thenReturn(true);
        when(activityRepo.findById(competition.getId())).thenReturn(java.util.Optional.of(competition));
    }

    public void setTrainingRepo() {
        when(activityRepo.existsById(training.getId())).thenReturn(true);
        when(activityRepo.findById(training.getId())).thenReturn(java.util.Optional.of(training));
    }

    @Test
    public void createTraining() {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 14:30; 10-10-2022 16:00", "yacht", List.of("captain"), null, null, false);
        ArgumentCaptor<Training> captor = ArgumentCaptor.forClass(Training.class);
        serviceCreateDelete.createTraining(user, request);
        verify(activityRepo).save(captor.capture());
        Training training = captor.getValue();
        Training expected = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"));

        assertThat(training.getId()).isEqualTo(expected.getId());
        assertThat(training.getOwner()).isEqualTo(expected.getOwner());
        assertThat(training.getTimeSlot()).isEqualTo(expected.getTimeSlot());
        assertThat(training.getBoat()).isEqualTo(expected.getBoat());
        assertThat(training.getPositions()).isEqualTo(expected.getPositions());
    }


    @Test
    public void createCompetition() {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 14:30; 10-10-2022 16:00", "yacht", List.of("captain"), "organization", "female", true);
        ArgumentCaptor<Competition> captor = ArgumentCaptor.forClass(Competition.class);
        serviceCreateDelete.createCompetition(user, request);
        verify(activityRepo).save(captor.capture());
        Competition competition = captor.getValue();
        Competition expected = new Competition(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"), "organization", "female", true);

        assertThat(competition.getId()).isEqualTo(expected.getId());
        assertThat(competition.getOwner()).isEqualTo(expected.getOwner());
        assertThat(competition.getTimeSlot()).isEqualTo(expected.getTimeSlot());
        assertThat(competition.getBoat()).isEqualTo(expected.getBoat());
        assertThat(competition.getPositions()).isEqualTo(expected.getPositions());
        assertThat(competition.getOrganization()).isEqualTo(expected.getOrganization());
        assertThat(competition.getGender()).isEqualTo(expected.getGender());
        assertThat(competition.getCompetitive()).isEqualTo(expected.getCompetitive());
    }

    @Test
    public void editCompetitionOneField() throws UnauthorizedException, ActivityNotFoundException {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 14:30; 10-10-2022 16:00", "yacht", List.of("captain"), "organization", "female", true);
        competition.setId(2L);
        setCompetitionRepo();
        serviceEdit.editActivity(user, competition.getId(), request);
        ArgumentCaptor<Competition> captor = ArgumentCaptor.forClass(Competition.class);
        verify(activityRepo).save(captor.capture());
        Competition edited = captor.getValue();

        assertThat(edited.getBoat()).isEqualTo("yacht");
    }

    @Test
    public void editCompetitionMoreFields() throws UnauthorizedException, ActivityNotFoundException {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 13:00; 10-10-2022 16:00", "boat", List.of("captain", "cox"), "organization", "female", true);
        competition.setId(2L);
        setCompetitionRepo();
        serviceEdit.editActivity(user, competition.getId(), request);
        ArgumentCaptor<Competition> captor = ArgumentCaptor.forClass(Competition.class);
        verify(activityRepo).save(captor.capture());
        Competition edited = captor.getValue();

        assertThat(edited.getTimeSlot()).isEqualTo(TimeSlot.getTimeSlot("10-10-2022 13:00; 10-10-2022 16:00"));
        assertThat(edited.getPositions()).isEqualTo(List.of("captain", "cox"));
    }


    @Test
    public void editCompetitionAllFields() throws UnauthorizedException, ActivityNotFoundException {
        ActivityRequestModel request = new ActivityRequestModel("11-10-2022 13:00; 11-10-2022 16:00", "yacht", List.of("captain", "cox"), "gryffindor", "male", false);
        competition.setId(2L);
        setCompetitionRepo();
        serviceEdit.editActivity(user, competition.getId(), request);
        ArgumentCaptor<Competition> captor = ArgumentCaptor.forClass(Competition.class);
        verify(activityRepo).save(captor.capture());
        Competition edited = captor.getValue();

        assertThat(edited.getTimeSlot()).isEqualTo(TimeSlot.getTimeSlot("11-10-2022 13:00; 11-10-2022 16:00"));
        assertThat(edited.getBoat()).isEqualTo("yacht");
        assertThat(edited.getPositions()).isEqualTo(List.of("captain", "cox"));
        assertThat(edited.getOrganization()).isEqualTo("gryffindor");
        assertThat(edited.getGender()).isEqualTo("male");
        assertThat(edited.getCompetitive()).isEqualTo(false);
    }

    @Test
    public void editTrainingOneField() throws UnauthorizedException, ActivityNotFoundException {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 14:30; 10-10-2022 16:00", "yacht", List.of("captain"), null, null, false);
        training.setId(1L);
        setTrainingRepo();
        serviceEdit.editActivity(new NetId("zosia"), training.getId(), request);
        ArgumentCaptor<Training> captor = ArgumentCaptor.forClass(Training.class);
        verify(activityRepo).save(captor.capture());
        Training edited = captor.getValue();

        assertEquals("zosia", edited.getOwner().getNetIdValue());
        assertEquals("10-10-2022 14:30;10-10-2022 16:00", edited.getTimeSlot().toString());
        assertEquals("yacht", edited.getBoat());
        assertEquals(List.of("captain"), edited.getPositions());
    }


    @Test
    public void editTrainingSomeFields() throws UnauthorizedException, ActivityNotFoundException {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 14:00; 10-11-2022 16:00", "yacht", List.of("captain"), null, null, false);
        training.setId(1L);
        setTrainingRepo();
        serviceEdit.editActivity(new NetId("zosia"), training.getId(), request);
        ArgumentCaptor<Training> captor = ArgumentCaptor.forClass(Training.class);
        verify(activityRepo).save(captor.capture());
        Training edited = captor.getValue();

        assertEquals("zosia", edited.getOwner().getNetIdValue());
        assertEquals("10-10-2022 14:00;10-11-2022 16:00", edited.getTimeSlot().toString());
        assertEquals("yacht", edited.getBoat());
        assertEquals(List.of("captain"), edited.getPositions());
    }

    @Test
    public void editTrainingAllFields() throws UnauthorizedException, ActivityNotFoundException {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 14:00; 10-11-2022 16:00", "yacht", List.of("captain"), null, null, false);
        training.setId(1L);
        setTrainingRepo();
        serviceEdit.editActivity(new NetId("zosia"), training.getId(), request);
        ArgumentCaptor<Training> captor = ArgumentCaptor.forClass(Training.class);
        verify(activityRepo).save(captor.capture());
        Training edited = captor.getValue();

        assertEquals("zosia", edited.getOwner().getNetIdValue());
        assertEquals("10-10-2022 14:00;10-11-2022 16:00", edited.getTimeSlot().toString());
        assertEquals("yacht", edited.getBoat());
        assertEquals(List.of("captain"), edited.getPositions());
    }

    @Test
    public void editTrainingUnauthorized() {
        ActivityRequestModel request = new ActivityRequestModel("10-10-2022 14:00; 10-11-2022 16:00", "yacht", List.of("captain"), null, null, false);
        training.setId(1L);
        setTrainingRepo();

        assertThrows(UnauthorizedException.class, () -> serviceEdit.editActivity(new NetId("harry"), training.getId(), request));
    }


    @Test
    public void editCompetitionUnauthorized() {
        ActivityRequestModel request = new ActivityRequestModel("11-10-2022 13:00; 11-10-2022 16:00", "yacht", List.of("captain", "cox"), "gryffindor", "male", false);
        competition.setId(2L);
        setCompetitionRepo();

        assertThrows(UnauthorizedException.class, () -> serviceEdit.editActivity(new NetId("zosia"), competition.getId(), request));
    }

    @Test
    public void getAllWithOneActivity() {
        when(activityRepo.findAll()).thenReturn(List.of(training));
        List<Activity> activities = serviceGet.getAll();

        assertEquals(1, activities.size());
        assertEquals(training, activities.get(0));
    }


    @Test
    public void getAllWithTrainingsAndCompetitions() {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        List<Activity> activities = serviceGet.getAll();

        assertEquals(2, activities.size());
        assertEquals(training, activities.get(0));
        assertEquals(competition, activities.get(1));
    }

    @Test
    public void getTrainingsWithTrainingsAndCompetitions() {
        when(activityRepo.findAll()).thenReturn(List.of(training));
        List<Training> trainings = serviceGet.getTrainings();

        assertEquals(1, trainings.size());
        assertEquals(training, trainings.get(0));
    }

    @Test
    public void getAllWithNoActivities() {
        when(activityRepo.findAll()).thenReturn(List.of());
        List<Activity> activities = serviceGet.getAll();

        assertEquals(0, activities.size());
    }

    @Test
    public void getAllCompetitionsWithOneCompetition() {
        when(activityRepo.findAll()).thenReturn(List.of(competition));
        List<Competition> competitions = serviceGet.getCompetitions();

        assertEquals(1, competitions.size());
        assertEquals(competition, competitions.get(0));
    }

    @Test
    public void getAllCompetitionsWithNoCompetitions() {
        when(activityRepo.findAll()).thenReturn(List.of());
        List<Competition> competitions = serviceGet.getCompetitions();

        assertEquals(0, competitions.size());
    }

    @Test
    public void getAllCompetitionsWithTrainingsAndCompetitions() {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        List<Competition> competitions = serviceGet.getCompetitions();

        assertEquals(1, competitions.size());
        assertEquals(competition, competitions.get(0));
    }

    @Test
    public void getAllTrainingsWithJustCompetitions() {
        when(activityRepo.findAll()).thenReturn(List.of(competition));
        List<Training> trainings = serviceGet.getTrainings();

        assertEquals(0, trainings.size());
    }

    @Test
    public void getActivityByIdWithNoActivities() {
        when(activityRepo.findAll()).thenReturn(List.of());

        assertThrows(ActivityNotFoundException.class, () -> serviceGet.getById(1L));
    }

    @Test
    public void getActivityByIdWithOneActivity() throws ActivityNotFoundException {
        when(activityRepo.findAll()).thenReturn(List.of(training));
        training.setId(1L);
        setTrainingRepo();
        Activity activity = serviceGet.getById(1L);

        assertEquals(training, activity);
    }

    @Test
    public void getActivityByIdWithTwoActivities() throws ActivityNotFoundException {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        competition.setId(2L);
        setCompetitionRepo();
        Activity activity = serviceGet.getById(2L);

        assertEquals(activity, competition);
    }

    @Test
    public void getActivityByIdWithTwoActivitiesAndWrongId() {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        assertThrows(ActivityNotFoundException.class, () -> serviceGet.getById(3L));
    }

    @Test
    public void deleteActivityByIdWithNoActivities() {
        when(activityRepo.findAll()).thenReturn(List.of());
        assertThrows(ActivityNotFoundException.class, () -> serviceCreateDelete.deleteById(new NetId("zosia"), 1L));
    }

    @Test
    public void deleteActivityByIdWithOneActivity() throws ActivityNotFoundException, UnauthorizedException {
        when(activityRepo.findAll()).thenReturn(List.of(training));
        training.setId(1L);
        setTrainingRepo();
        serviceCreateDelete.deleteById(new NetId("zosia"), 1L);
        verify(activityRepo, times(1)).deleteById(1L);
    }

    @Test
    public void deleteActivityByIdWithTwoActivities() throws ActivityNotFoundException, UnauthorizedException {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        training.setId(1L);
        competition.setId(2L);
        setTrainingRepo();
        setCompetitionRepo();
        serviceCreateDelete.deleteById(new NetId("zosia"), 1L);
        verify(activityRepo, times(1)).deleteById(1L);
    }

    @Test
    public void deleteActivityByIdWithTwoActivitiesAndWrongId() {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        training.setId(1L);
        competition.setId(2L);
        setTrainingRepo();
        setCompetitionRepo();

        assertThrows(ActivityNotFoundException.class, () -> serviceCreateDelete.deleteById(new NetId("zosia"), 3L));
    }

    @Test
    public void deleteActivityByIdWithTwoActivitiesAndWrongOwner() {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        training.setId(1L);
        competition.setId(2L);
        setTrainingRepo();
        setCompetitionRepo();

        assertThrows(UnauthorizedException.class, () -> serviceCreateDelete.deleteById(new NetId("harry"), 1L));
    }

    @Test
    public void deleteActivityByUserWithNoActivities() {
        when(activityRepo.findAll()).thenReturn(List.of());
        assertThrows(ActivityNotFoundException.class, () -> serviceCreateDelete.deleteByUser(new NetId("zosia"), new NetId("zosia")));
    }

    @Test
    public void deleteActivityByUserWithOneActivity() throws ActivityNotFoundException, UnauthorizedException {
        when(activityRepo.findAll()).thenReturn(List.of(training));
        serviceCreateDelete.deleteByUser(new NetId("zosia"), new NetId("zosia"));
        verify(activityRepo, times(1)).deleteAll(List.of(training));
    }

    @Test
    public void deleteActivityByUserWithTwoActivities() throws ActivityNotFoundException, UnauthorizedException {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        serviceCreateDelete.deleteByUser(new NetId("zosia"), new NetId("zosia"));
        verify(activityRepo, times(1)).deleteAll(List.of(training));
    }

    @Test
    public void deleteActivityByUserWithTwoActivitiesAndWrongOwner() {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        assertThrows(UnauthorizedException.class, () -> serviceCreateDelete.deleteByUser(new NetId("zosia"), new NetId("paula")));
    }

    @Test
    public void getActivitiesByUserWithNoActivities() {
        when(activityRepo.findAll()).thenReturn(List.of());
        assertThrows(ActivityNotFoundException.class, () -> serviceGet.getByUsername("zosia"));
    }

    @Test
    public void getActivitiesByUserWithOneActivity() throws ActivityNotFoundException {
        when(activityRepo.findAll()).thenReturn(List.of(training));
        assertEquals(List.of(training), serviceGet.getByUsername("zosia"));
    }

    @Test
    public void getActivitiesByUserWithTwoActivities() throws ActivityNotFoundException {
        when(activityRepo.findAll()).thenReturn(List.of(training, competition));
        assertEquals(List.of(training), serviceGet.getByUsername("zosia"));
    }

    @Test
    public void equalHashCode() {
        Training training1 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"));
        Training training2 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"));
        assertEquals(training1.hashCode(), training2.hashCode());
    }

    @Test
    public void notEqualHashCode() {
        Training training1 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"));
        Training training2 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "canoe", List.of("cox"));
        assertNotEquals(training1.hashCode(), training2.hashCode());
    }

    @Test
    public void EqualTrainingString() {
        Training training1 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"));
        Training training2 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"));
        assertEquals(training1.toString(), training2.toString());
    }

    @Test
    public void notEqualTrainingString() {
        Training training1 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "yacht", List.of("captain"));
        Training training2 = new Training(user, TimeSlot.getTimeSlot("10-10-2022 14:30; 10-10-2022 16:00"), "canoe", List.of("cox"));
        assertNotEquals(training1.toString(), training2.toString());
    }

    @Test
    public void usernameToString() {
        Username username = new Username("Minouk");
        assertEquals("Minouk", username.toString());
    }

    @Test
    public void invalidTimeSlot() throws ParseException {
        TimeSlot timeSlot = new TimeSlot("invalid;timeslot");
    }

    @Test
    public void getMultipleTimeSlots() {
        List<String> timeSlots = List.of("10-10-2022 14:00;10-10-2022 16:00", "22-01-2023 14:00;22-01-2023 16:00");
        assertEquals(2, TimeSlot.getTimeSlots(timeSlots).size());
        assertEquals("10-10-2022 14:00;10-10-2022 16:00", TimeSlot.getTimeSlots(timeSlots).get(0).toString());
        assertEquals("22-01-2023 14:00;22-01-2023 16:00", TimeSlot.getTimeSlots(timeSlots).get(1).toString());
    }

}