package nl.tudelft.sem.template.example.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.example.domain.chainOfResponsability.*;
import nl.tudelft.sem.template.example.domain.transferObject.RequestMatch;
import nl.tudelft.sem.template.example.domain.transferObject.TransferMatch;
import nl.tudelft.sem.template.example.domain.utils.ServerUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A DDD service for registering a new user.
 */
@Service
public class MatcherComputingService {
    private final transient MatcherRepository matcherRepository;
    private final transient ServerUtils serverUtils;

    /**
     * Constructor for MatcherService.
     *
     * @param matcherRepository
     * @param serverUtils
     */
    public MatcherComputingService(MatcherRepository matcherRepository, ServerUtils serverUtils) {
        this.matcherRepository = matcherRepository;
        this.serverUtils = serverUtils;
    }


    transient Validator handler;
    transient List<TimeSlot> timeSlots;

    /**
     * Method to match a participant.
     *
     * @param rm
     * @return list of matches
     */
    public List<TransferMatch> computeMatch(RequestMatch rm) {
        List<TransferMatch> res = new ArrayList<>();
        handler = setValidators();
        timeSlots = TimeSlot.getTimeSlots(rm.getTimeSlots());
        for (Activity activity : getActivities()) {
            for (String position : activity.getPositions()) {
                if (handler.handle(activity, position, rm.getParticipant(), timeSlots))
                    res.add(new TransferMatch((long) activity.getId(), position, activity.getTimeSlot().toString(),
                            rm.getParticipant().getNetId().toString(), activity.getOwner().toString()));
            }
        }
        return res;

    }

    /**
     * Method to set the validators.
     *
     * @return validator
     */
    public Validator setValidators() {
        Validator handler = new TimeSlotValidator();
        Validator positionValidator = new PositionValidator();
        Validator competitionValidator = new CompetitionValidator();
        Validator certificateValidator = new CertificateValidator();
        handler.setNext(positionValidator);
        positionValidator.setNext(competitionValidator);
        competitionValidator.setNext(certificateValidator);

        return handler;
    }

    /**
     * Method to get all activities.
     *
     * @return list of activities
     */
    public List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        activities.addAll(getTrainings());
        activities.addAll(getCompetitions());
        return activities;
    }

    /**
     * Method to get all trainings.
     *
     * @return list of trainings
     */
    public List<Training> getTrainings() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(serverUtils.getTrainings(), new TypeReference<List<Training>>() {
        });
    }

    /**
     * Method to get all competitions.
     *
     * @return list of competitions
     */
    public List<Competition> getCompetitions() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(serverUtils.getCompetitions(), new TypeReference<List<Competition>>() {
        });

    }
}
