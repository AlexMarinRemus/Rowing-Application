package nl.tudelft.sem.template.example.domain.participant;

import nl.tudelft.sem.template.example.domain.models.RequetsTransferMatchModel;
import nl.tudelft.sem.template.example.domain.transferClasses.RequestMatch;
import nl.tudelft.sem.template.example.domain.transferClasses.TransferMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ParticipantDetailsServiceTest {


    ParticipantDetailsService ps;
    ParticipantService ps2;
    private ParticipantRepository mockParticipantRepository;

    @BeforeEach
    public void Setup(){
        mockParticipantRepository=mock(ParticipantRepository.class);
        ps2=new ParticipantService(mockParticipantRepository);
        ps=new ParticipantDetailsService(mockParticipantRepository,ps2);
    }


    @Test
    void getParticipantTest() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        assertTrue(ps2.getParticipant(netId).equals(p));
    }

    @Test
    void getParticipantTestNull() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->ps2.getParticipant(netId));
    }

    @Test
    void getParticipantPositions() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        List<String> result= new ArrayList<>();
        result.add("coach");
        result.add("cox");
        assertTrue(ps.getParticipantPositions(netId).equals(result));
    }

    @Test
    void getParticipantPositionsNull() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        when(ps2.getParticipant(netId).getPositionManager().getPositions()).thenReturn(null);
        List<String> result= new ArrayList<>();
        result.add("coach");
        result.add("cox");
        assertThrows(ResponseStatusException.class,()->ps.getParticipantPositions(netId));
    }
    @Test
    void getParticipantCertificate() {
        NetId netId= new NetId("user");
        Certificate cer= new Certificate("C4");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",cer,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        assertTrue(ps.getParticipantCertificate(netId).equals(cer.toString()));
    }

    @Test
    void getParticipantCertificateNull() {
        NetId netId= new NetId("user");
        Certificate cer= new Certificate("C4");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"m",cer,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        when(ps2.getParticipant(netId).getCertificate()).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->ps.getParticipantCertificate(netId));
    }

    @Test
    void getParticipantOrganization() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        assertTrue(ps.getParticipantOrganization(netId).equals("org"));
    }

    @Test
    void getParticipantOrganizationNull() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        when(ps2.getParticipant(netId).getOrganization()).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->ps.getParticipantOrganization(netId));
    }

    @Test
    void getParticipantGender() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        assertTrue(ps.getParticipantGender(netId).equals("M"));
    }

    @Test
    void getParticipantGenderNull() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),null,null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        when(ps2.getParticipant(netId).getGender()).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->ps.getParticipantGender(netId));
    }

    @Test
    void getParticipantLevel() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        assertTrue(ps.getParticipantLevel(netId).equals(true));
    }

    @Test
    void getParticipantLevelNull() {
        NetId netId= new NetId("user");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",null);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        when(ps2.getParticipant(netId).getLevel()).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->ps.getParticipantLevel(netId));
    }

    @Test
    void getRequestMatch() {
        NetId netId= new NetId("user");
        List<String> timeslots= new ArrayList<>();
        timeslots.add("23-11-2022 22:30;24-11-2022 22:30");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",null,"org",true);
        when(mockParticipantRepository.findByNetId(netId)).thenReturn(Optional.of(p));
        RequestMatch rm= new RequestMatch(p,timeslots);
        assertTrue(ps2.getRequestMatch(netId,timeslots).getTimeSlots().equals(rm.getTimeSlots()));
        assertTrue(ps2.getRequestMatch(netId,timeslots).getParticipant().equals(rm.getParticipant()));
    }

    @Test
    void addParticipant(){
        NetId netId= new NetId("user");
        List<String> timeslots= new ArrayList<>();
        timeslots.add("23-11-2022 22:30;24-11-2022 22:30");
        Participant p= new Participant(netId,new PositionManager("coach,cox"),"M",new Certificate("C4"),"org",true);
        mockParticipantRepository.save(p);
        Participant addedP =ps2.addParticipant(netId,new PositionManager("coach,cox"),"M",new Certificate("C4"),"org",true);
        assertTrue(addedP.getLevel().equals(p.getLevel()));
        assertTrue(addedP.getCertificate().equals(p.getCertificate()));
        assertTrue(addedP.getOrganization().equals(p.getOrganization()));
        assertTrue(addedP.getId()==p.getId());
        assertTrue(addedP.getNetId().equals(p.getNetId()));
        assertTrue(addedP.getPositionManager().equals(p.getPositionManager()));
        assertTrue(addedP.getGender().equals(p.getGender()));
    }

    @Test
    void getTransferMatchTest(){
        RequetsTransferMatchModel r= new RequetsTransferMatchModel(1L,"cox","23-11-2022 22:30;24-11-2022 22:30","netId","owner");
        TransferMatch t= new TransferMatch(1L,"cox","23-11-2022 22:30;24-11-2022 22:30","netId","owner");
        TransferMatch rq= ps2.getTransferMatch(r);
        assertTrue(t.getPosition().equals(rq.getPosition()));
        assertTrue(t.getOwner().equals(rq.getOwner()));
        assertTrue(t.getTimeSlot().equals(rq.getTimeSlot()));
        assertTrue(t.getNetId().equals(rq.getNetId()));
        assertTrue(t.getActivityId().equals(rq.getActivityId()));
    }

}