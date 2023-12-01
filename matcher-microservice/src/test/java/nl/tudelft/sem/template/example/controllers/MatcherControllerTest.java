package nl.tudelft.sem.template.example.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.example.domain.*;
import nl.tudelft.sem.template.example.domain.transferObject.RequestMatch;
import nl.tudelft.sem.template.example.domain.transferObject.TransferMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager","matcherEditService","matcherComputingService"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class MatcherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private transient JwtTokenVerifier mockJwtTokenVerifier;

    @Autowired
    private transient AuthManager mockAuthenticationManager;

    @MockBean
    private transient MatcherEditService matcherEditService;

    @MockBean
    private transient MatcherComputingService matcherComputingService;

    @BeforeEach
    void setUp() {
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
    }


    @Test
    void requestMatchTest() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Participant participant = getParticipant();
        List<String> timeSlots = new ArrayList<>();
        timeSlots.add("20-12-2022 09:00;20-12-2022 11:00");

        RequestMatch requestMatch = new RequestMatch(participant,timeSlots);
        String jsonRequest = mapper.writeValueAsString(requestMatch);

        TransferMatch expected = new TransferMatch(1L,"cox","timeslot",
                "participant","owner");
        List<TransferMatch> lst = new ArrayList<>();
        lst.add(expected);
        when(matcherComputingService.computeMatch(any())).thenReturn(lst);

        ResultActions result = mockMvc.perform(post("/requestMatch")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
        result.andExpect(status().isOk());

        String response = result.andReturn().getResponse().getContentAsString();
        List<TransferMatch> transferMatches = mapper.readValue(response, new TypeReference<List<TransferMatch>>() {});
        TransferMatch transferMatch = transferMatches.get(0);

        assertThat(transferMatch.getActivityId().equals(expected.getActivityId()));

    }
    @Test
    void acceptedMatch() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
       TransferMatch tm = new TransferMatch(
               1L,"cox","20-12-2022 09:00;20-12-2022 11:00",
               "participant","owner");
        String jsonRequest = mapper.writeValueAsString(tm);
        Match m = new Match(tm.getNetId(),tm.getActivityId(),tm.getPosition());



        ResultActions result = mockMvc.perform(post("/acceptedMatch")
                .header("Authorization", "Bearer MockedToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
        result.andExpect(status().isOk());
        verify(matcherEditService).saveMatch(any(Match.class));

    }
    @Test
    void getPendingMatches() throws Exception {

        ObjectMapper mapper = new ObjectMapper();


        Match expectedMatch = new Match("participant",1L,"cox");
        when(matcherEditService.getAllMatches()).thenReturn(List.of(expectedMatch));



        ResultActions result = mockMvc.perform(get("/getAllPendingMatches")
                .header("Authorization", "Bearer MockedToken"));
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        List<Match> pendingMatches = mapper.readValue(response, new TypeReference<List<Match>>() {});
        assertEquals(pendingMatches.get(0).getNetId(),expectedMatch.getNetId());
        assertEquals(pendingMatches.get(0).getActivityId(),expectedMatch.getActivityId());
        assertEquals(pendingMatches.get(0).getPosition(),expectedMatch.getPosition());



    }


    private Participant getParticipant() {
        return new Participant(new NetId("participant"),
                new PositionManager("cox"),"M",
                new Certificate("C4"),"org",true);
    }


}