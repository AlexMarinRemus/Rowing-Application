package nl.tudelft.sem.template.example.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ActivityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private transient JwtTokenVerifier mockJwtTokenVerifier;

    @Autowired
    private transient AuthManager mockAuthenticationManager;

    @Autowired
    private transient ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
    }
    @Test
    void createTrainingTest() throws Exception {
        String jsonRequest = "{\"timeSlot\":\"20-12-2022 10:00;20-12-2022 14:00\",\"boat\":\"C4\",\"positions\":[\"cox\",\"coach\"]}";

        ResultActions result = mockMvc.perform(post("/activity/createTraining")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
        result.andExpect(status().isOk());

        String response = result.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Training training = mapper.readValue(response, Training.class);
        List<Activity> activities = activityRepository.findAll();
        assertThat(activities).containsExactly(training);
    }

    @Test
    void createCompetitionTest() throws Exception {
        String jsonRequest = "{\"timeSlot\":\"20-12-2022 10:00;20-12-2022 14:00\",\"boat\":\"C4\",\"positions\":[\"cox\",\"coach\"],\"organization\":\"org\",\"gender\":\"female\",\"competitive\":true}";

        ResultActions result = mockMvc.perform(post("/activity/createCompetition")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
        result.andExpect(status().isOk());

        String response = result.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Competition competition = mapper.readValue(response, Competition.class);
        List<Activity> activities = activityRepository.findAll();
        assertThat(activities).containsExactly(competition);
    }

    @Test
    void editTrainingTest() throws Exception {
        Training training = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        activityRepository.save(training);
        String jsonRequest = "{\"timeSlot\":\"20-12-2022 14:00;20-12-2022 18:00\",\"boat\":\"C4\",\"positions\":[\"cox\",\"coach\"]}";

        ResultActions result = mockMvc.perform(put("/activity/edit/{id}", training.getId())
                .header("Authorization", "Bearer MockedToken")
                .param("id", Long.toString(training.getId()))
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON));

        Activity edited = activityRepository.findById(training.getId()).get();
        assertThat(edited.getTimeSlot()).isEqualTo(TimeSlot.getTimeSlot("20-12-2022 14:00;20-12-2022 18:00"));
        assertThat(edited.getPositions()).isEqualTo(List.of("cox", "coach"));
    }

    @Test
    void getTrainingTest() throws Exception {
        Training training1 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        Training training2 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("21-12-2022 08:00;21-12-2022 10:00"), "8+", List.of("coach"));
        activityRepository.save(training1);
        activityRepository.save(training2);

        ResultActions result = mockMvc.perform(get("/activity/training")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        String response = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Training> trainings = mapper.readValue(response, new TypeReference<List<Training>>(){});

        assertThat(trainings.size()).isEqualTo(2);
        assertThat(trainings).containsExactlyInAnyOrder(training1, training2);
    }

    @Test
    void getCompetitionTest() throws Exception {
        Competition competition1 = new Competition(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"), "org", "female", true);
        Competition competition2 = new Competition(new NetId("ExampleUser"), TimeSlot.getTimeSlot("21-12-2022 08:00;21-12-2022 10:00"), "8+", List.of("coach"), "org", "male", false);
        activityRepository.save(competition1);
        activityRepository.save(competition2);

        ResultActions result = mockMvc.perform(get("/activity/competition")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        String response = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Competition> competitions = mapper.readValue(response, new TypeReference<List<Competition>>(){});

        assertThat(competitions.size()).isEqualTo(2);
        assertThat(competitions).containsExactlyInAnyOrder(competition1, competition2);
    }

    @Test
    void deleteByIdTest() throws Exception {
        Training training = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        activityRepository.save(training);

        String id = Long.toString(training.getId());
        ResultActions result = mockMvc.perform(delete("/activity/deleteId/" + id)
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        assertThat(activityRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void deleteByUserTest() throws Exception {
        Training training = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        activityRepository.save(training);

        NetId netId = new NetId("ExampleUser");
        ResultActions result = mockMvc.perform(delete("/activity/deleteUser/ExampleUser")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        assertThat(activityRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void getByNetIdTest() throws Exception {
        Training training1 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        Training training2 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("21-12-2022 08:00;21-12-2022 10:00"), "8+", List.of("coach"));

        activityRepository.save(training1);
        activityRepository.save(training2);

        ResultActions result = mockMvc.perform(get("/activity/user")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));
        String response = result.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Training> received = mapper.readValue(response, new TypeReference<List<Training>>(){});

        assertThat(received.size()).isEqualTo(2);
        assertThat(received).containsExactlyInAnyOrder(training1, training2);
    }

    @Test
    void getById() throws Exception {
        Training training1 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        Training training2 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("21-12-2022 08:00;21-12-2022 10:00"), "8+", List.of("coach"));

        activityRepository.save(training1);
        activityRepository.save(training2);

        long id2 = training2.getId();

        ResultActions result = mockMvc.perform(get("/activity/activityId/"+id2)
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));
        String response = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        Training received = mapper.readValue(response, Training.class);

        assertThat(received).isEqualTo(training2);
    }

    @Test
    void getOwnerById() throws Exception {
        Training training1 = new Training(new NetId("ExampleUser1"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        Training training2 = new Training(new NetId("ExampleUser2"), TimeSlot.getTimeSlot("21-12-2022 08:00;21-12-2022 10:00"), "8+", List.of("coach"));

        activityRepository.save(training1);
        activityRepository.save(training2);

        long id1 = training1.getId();
        long id2 = training2.getId();

        ResultActions result = mockMvc.perform(get("/activity/user/"+id1)
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));
        String response = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        NetId received = mapper.readValue(response, NetId.class);

        ResultActions result2 = mockMvc.perform(get("/activity/user/"+id2)
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));
        String response2 = result.andReturn().getResponse().getContentAsString();
        NetId received2 = mapper.readValue(response2, NetId.class);

        assertThat(received).isEqualTo(new NetId("ExampleUser1"));
        assertThat(received2).isEqualTo(new NetId("ExampleUser2"));
    }

    @Test
    void getByUsernameTest() throws Exception {
        Training training1 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("20-12-2022 10:00;20-12-2022 14:00"), "C4", List.of("cox"));
        Training training2 = new Training(new NetId("ExampleUser"), TimeSlot.getTimeSlot("21-12-2022 08:00;21-12-2022 10:00"), "8+", List.of("coach"));

        activityRepository.save(training1);
        activityRepository.save(training2);

        String userName = training1.getOwner().getNetIdValue();

        ResultActions result = mockMvc.perform(get("/activity/" + userName)
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));
        String response = result.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Training> received = mapper.readValue(response, new TypeReference<List<Training>>(){});

        assertThat(received.size()).isEqualTo(2);
        assertThat(received).containsExactlyInAnyOrder(training1, training2);
    }
}