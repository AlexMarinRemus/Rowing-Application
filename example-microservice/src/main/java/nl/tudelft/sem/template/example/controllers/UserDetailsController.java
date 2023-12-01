package nl.tudelft.sem.template.example.controllers;

import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.domain.participant.ParticipantDetailsService;
import nl.tudelft.sem.template.example.domain.participant.NetId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("userDetails")
public class UserDetailsController {
    private final transient AuthManager authManager;
    private final transient ParticipantDetailsService participantDetailsService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     */
    @Autowired
    public UserDetailsController(AuthManager authManager, ParticipantDetailsService participantDetailsService) {
        this.authManager = authManager;
        this.participantDetailsService = participantDetailsService;
    }

    /**
     * Gets positions of the user.
     * @return list of positions
     */
    @GetMapping("/positions")
    public List<String> getPositions() {

        try {
            List<String> positions;
            NetId participantName = new NetId(authManager.getNetId());
             positions = participantDetailsService.getParticipantPositions(participantName);
            return positions;
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Gets the certificate of the user.
     * @return certificate
     */
    @GetMapping("/certificate")
    public String getCertificate() {

        try {

            NetId participantName = new NetId(authManager.getNetId());
            String certificate = participantDetailsService.getParticipantCertificate(participantName);
            return certificate;
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Gets gender of the user.
     * @return gender
     */
    @GetMapping("/gender")
    public String getGender() {

        try {

            NetId participantName = new NetId(authManager.getNetId());
            String gender = participantDetailsService.getParticipantGender(participantName);
            return gender;
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Gets the level of the user.
     * @return level
     */
    @GetMapping("/level")
    public Boolean getLevel() {
        try {
            NetId participantName = new NetId(authManager.getNetId());
            Boolean level = participantDetailsService.getParticipantLevel(participantName);
            return level;
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    /**
     * Gets the organization of the user.
     * @return organization
     */
    @GetMapping("/organization")
    public String getOrganization() {
        try {
            NetId participantName = new NetId(authManager.getNetId());
            String organization = participantDetailsService.getParticipantOrganization(participantName);
            return organization;
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
