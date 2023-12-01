package nl.tudelft.sem.template.example.controllers;

import nl.tudelft.sem.template.example.authentication.AuthManager;
import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Competition;
import nl.tudelft.sem.template.example.domain.Training;
import nl.tudelft.sem.template.example.domain.utils.ServerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class CommunicationController {

    private final transient AuthManager authManager;
    private final transient ServerUtils serverUtils;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     * @param serverUtils
     */
    @Autowired
    public CommunicationController(AuthManager authManager, ServerUtils serverUtils) {
        this.authManager = authManager;
        this.serverUtils = serverUtils;
    }

    /**
     * Get the list of activities.
     * @return list of activities
     */
    @GetMapping("/getActivities")
    public List<Activity> getActivities() {
        return serverUtils.getActivities();
    }

    /**
     * Get the list of trainings.
     * @return list of trainings
     */
    @GetMapping("/getTrainings")
    public List<Training> getTrainings() {
        return serverUtils.getTrainings();
    }

    /**
     * Get the list of competitions.
     * @return list of competitions
     */
    @GetMapping("/getCompetitions")
    public List<Competition> getCompetitions() {
        return serverUtils.getCompetitions();
    }
}
