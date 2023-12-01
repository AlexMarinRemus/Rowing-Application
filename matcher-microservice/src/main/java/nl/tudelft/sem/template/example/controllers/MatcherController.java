package nl.tudelft.sem.template.example.controllers;

import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.domain.Match;
import nl.tudelft.sem.template.example.domain.MatcherComputingService;
import nl.tudelft.sem.template.example.domain.MatcherEditService;
import nl.tudelft.sem.template.example.domain.transferObject.RequestMatch;
import nl.tudelft.sem.template.example.domain.transferObject.TransferMatch;
import nl.tudelft.sem.template.example.domain.utils.ServerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class MatcherController {

    private final transient AuthManager authManager;
    private final transient MatcherEditService matcherEditService;
    private final transient MatcherComputingService matcherComputingService;

    private final transient ServerUtils serverUtils;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     */
    @Autowired
    public MatcherController(AuthManager authManager, MatcherEditService matcherEditService, MatcherComputingService matcherComputingService, ServerUtils serverUtils) {
        this.authManager = authManager;
        this.matcherEditService = matcherEditService;
        this.serverUtils= serverUtils;
        this.matcherComputingService = matcherComputingService;
    }

    /**
     * Gets example by id.
     *
     * @return the example found in the database with the given id
     */
    @PostMapping("/requestMatch")
    public List<TransferMatch> requestMatch(@RequestBody RequestMatch rm) {
        System.out.println(rm.getTimeSlots());
        List<TransferMatch> lst = matcherComputingService.computeMatch(rm) ;
        return lst ;

    }

    /**
     *
     * @param tm
     */
    @PostMapping("/acceptedMatch")
    public void acceptedMatch(@RequestBody TransferMatch tm){
        Match m = new Match(tm.getNetId(),tm.getActivityId(),tm.getPosition());
        matcherEditService.saveMatch(m);
        serverUtils.sendPendingUser(tm);
    }

    /**
     * Gets all pending matches.
     * @return list of pending matches
     */
    @GetMapping("/getAllPendingMatches")
    public List<Match> acceptedMatch(){
        return matcherEditService.getAllMatches();
    }

    /**
     * Send accepted user.
     * @param request
     */
    @PostMapping("/sendAcceptedUsers")
    public void sendAcceptedUsers(@RequestBody List<TransferMatch> request){
        List<TransferMatch> acceptedMatches= request;
        matcherEditService.removeMatches(acceptedMatches);
        serverUtils.sendAcceptedUsers(acceptedMatches);
    }
}
