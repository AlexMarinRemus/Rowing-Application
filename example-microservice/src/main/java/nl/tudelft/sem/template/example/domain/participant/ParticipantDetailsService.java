package nl.tudelft.sem.template.example.domain.participant;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service for adding new Participants and their details
 */
@Service
public class ParticipantDetailsService {
    private final transient ParticipantRepository participantRepository;
    private final transient ParticipantService participantService;
    /**
     * Instantiates a new ParticipantService
     *
     * @param participantRepository participant repository
     */
    public ParticipantDetailsService(ParticipantRepository participantRepository, ParticipantService participantService) {
        this.participantRepository = participantRepository;
        this.participantService = participantService;
    }


    /**
     * Get the positions of the participant by netId.
     * @param netId
     * @return positions
     */
    public List<String> getParticipantPositions(NetId netId) {

        if(participantService.getParticipant(netId).getPositionManager().getPositions()!=null){
            List<String> positions= participantService.getParticipant(netId).getPositionManager().getPositions();
            return positions;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get the certificate of the participant by netId.
     * @param netId
     * @return certificate
     */
    public String getParticipantCertificate(NetId netId) {
        if(participantService.getParticipant(netId).getCertificate()!=null){
            String certificate= participantService.getParticipant(netId).getCertificate().toString();
            return certificate;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets the organization of the participant by netId.
     * @param netId
     * @return organization
     */
    public String getParticipantOrganization(NetId netId) {
        if(participantService.getParticipant(netId).getOrganization()!=null){
            String organization= participantService.getParticipant(netId).getOrganization();
            return organization;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets the gender of the participant by netId.
     * @param netId
     * @return gender
     */
    public String getParticipantGender(NetId netId) {
        if(participantService.getParticipant(netId).getGender()!=null){
            String gender= participantService.getParticipant(netId).getGender();
            return gender;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets the level of the participant by netId.
     * @param netId
     * @return level
     */
    public Boolean getParticipantLevel(NetId netId) {
        if(participantService.getParticipant(netId).getLevel()!=null){
            Boolean level= participantService.getParticipant(netId).getLevel();
            return level;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}
