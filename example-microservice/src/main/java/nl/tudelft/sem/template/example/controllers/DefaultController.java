package nl.tudelft.sem.template.example.controllers;

import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.domain.models.ParticipationRequestModel;
import nl.tudelft.sem.template.example.domain.models.RequestMatchModel;
import nl.tudelft.sem.template.example.domain.models.RequetsTransferMatchModel;
import nl.tudelft.sem.template.example.domain.participant.*;
import nl.tudelft.sem.template.example.domain.transferClasses.RequestMatch;
import nl.tudelft.sem.template.example.domain.transferClasses.TransferMatch;
import nl.tudelft.sem.template.example.domain.utils.ServerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class DefaultController {

    private final transient AuthManager authManager;
    private final transient ParticipantDetailsService participantDetailsService;
    private final transient ServerUtils serverUtils;
    private final transient ParticipantService participantService;
    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     */
    @Autowired
    public DefaultController(AuthManager authManager, ParticipantDetailsService participantDetailsService, ServerUtils serverUtils, ParticipantService participantService) {
        this.authManager = authManager;
        this.participantDetailsService = participantDetailsService;
        this.serverUtils = serverUtils;
        this.participantService = participantService;
    }

    /**
     * Gets example by id.
     *
     * @return the example found in the database with the given id
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello " + authManager.getNetId());

    }

    /**
     * Endpoint for adding a new participant
     *
     * @param request The registration model
     * @return 200 OK if the registration is successful
     * @throws Exception
     */
    @PostMapping("/details")
    public ResponseEntity addDetails(@RequestBody ParticipationRequestModel request){
        try{
            NetId netId= new NetId(authManager.getNetId());
            PositionManager positionManager= new PositionManager(request.getPositions());
            Certificate certificate= new Certificate(request.getCertificate());
            String gender= request.getGender();
            //here we need to throw an exception if the entered certificate is wrong
            String organization= request.getOrganization();
            Boolean level= request.getLevel();
            participantService.addParticipant(netId,positionManager,gender,certificate,organization,level);
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Fine");
    }

    /**
     * Endpoint for getting the matches of a participant
     * @param request
     * @return List of matches
     */
    @GetMapping("/requestMatch")
    public List<TransferMatch> requestMatch(@RequestBody RequestMatchModel request) {
        List<String> timeSlots = request.getTimeslots();
        NetId netId= new NetId(authManager.getNetId());
        RequestMatch rm = participantService.getRequestMatch(netId,timeSlots);

        return serverUtils.sendRequestMatch(rm);

    }

    /**
     * Endpoint for requesting a transfer match.
     * @param request
     * @return 200 OK if the request is successful
     */
    @PostMapping("/acceptedMatch")
    public ResponseEntity<String> requestTransferMatch(@RequestBody RequetsTransferMatchModel request){
        TransferMatch tm = participantService.getTransferMatch(request);
        return serverUtils.sendAcceptedMatch(tm);
    }
}
