package nl.tudelft.sem.template.example.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.example.domain.models.ParticipationRequestModel;
import nl.tudelft.sem.template.example.domain.participant.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ExampleTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private transient JwtTokenVerifier mockJwtTokenVerifier;

    @Autowired
    private transient AuthManager mockAuthenticationManager;

    @Autowired
    private transient ParticipantRepository participantRepository;

    @BeforeEach
    void setUp() {
        participantRepository.deleteAll();
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
        Participant p = new Participant(new NetId("ExampleUser"), new PositionManager("cox"), "M", new Certificate("C4"), "org", true);
        participantRepository.save(p);
    }

    @Test
    public void helloWorld() throws Exception {
        // Arrange
        // Notice how some custom parts of authorisation need to be mocked.
        // Otherwise, the integration test would never be able to authorise as the authorisation server is offline.
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");

        // Act
        // Still include Bearer token as AuthFilter itself is not mocked
        ResultActions result = mockMvc.perform(get("/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken"));

        // Assert
        result.andExpect(status().isOk());

        String response = result.andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo("Hello ExampleUser");

    }

    @Test
    public void detailsTest() throws Exception {
        String jsonRequest = "{\"positions\":\"cox\",\"certificate\":\"C4\",\"gender\":\"M\",\"organization\":\"org\",\"level\":\"true\"}";
        ResultActions result = mockMvc.perform(post("/details")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
        result.andExpect(status().isOk());
        assertThat(participantRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void getPositionsTest() throws Exception {
        Participant p = new Participant(new NetId("ExampleUser"), new PositionManager("cox"), "M", new Certificate("C4"), "org", true);
//        participantRepository.save(p);

        ResultActions result = mockMvc.perform(get("/userDetails/positions")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        String response = result.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<String> positions = mapper.readValue(response, new TypeReference<List<String>>(){});

        assertThat(positions.size()).isEqualTo(1);
        assertThat(positions).containsExactly("cox");
    }

    @Test
    public void getCertificateTest() throws Exception {
        Participant p = new Participant(new NetId("ExampleUser"), new PositionManager("cox"), "M", new Certificate("C4"), "org", true);
//        participantRepository.save(p);

        ResultActions result = mockMvc.perform(get("/userDetails/certificate")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        String response = result.andReturn().getResponse().getContentAsString();
        assertThat(response).isEqualTo("C4");
    }

    @Test
    public void getGenderTest() throws Exception {
        Participant p = new Participant(new NetId("ExampleUser"), new PositionManager("cox"), "M", new Certificate("C4"), "org", true);
//        participantRepository.save(p);

        ResultActions result = mockMvc.perform(get("/userDetails/gender")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        String response = result.andReturn().getResponse().getContentAsString();
        assertThat(response).isEqualTo("M");
    }

    @Test
    public void getLevelTest() throws Exception {
        Participant p = new Participant(new NetId("ExampleUser"), new PositionManager("cox"), "M", new Certificate("C4"), "org", true);
//        participantRepository.save(p);

        ResultActions result = mockMvc.perform(get("/userDetails/level")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        String response = result.andReturn().getResponse().getContentAsString();
        assertThat(response).isEqualTo("true");
    }

    @Test
    public void getOrganizationTest() throws Exception {
        Participant p = new Participant(new NetId("ExampleUser"), new PositionManager("cox"), "M", new Certificate("C4"), "org", true);
//        participantRepository.save(p);

        ResultActions result = mockMvc.perform(get("/userDetails/organization")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON));

        String response = result.andReturn().getResponse().getContentAsString();
        assertThat(response).isEqualTo("org");
    }
}
