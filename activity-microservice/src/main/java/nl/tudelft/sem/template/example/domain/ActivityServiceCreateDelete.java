package nl.tudelft.sem.template.example.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityServiceCreateDelete {
    private final transient ActivityRepository activityRepository;

    /**
     * Constructor for ActivityService.
     * @param activityRepository the repository for activities
     */
    public ActivityServiceCreateDelete(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Adds a training to the database.
     * @param username
     * @param request
     * @return new training
     */
    public Training createTraining(NetId username, ActivityRequestModel request) {
        Training training = new Training(username, request.getTimeSlot(), request.getBoat(), request.getPositions());
        activityRepository.save(training);
        return training;
    }

    /**
     * Adds a competition to the database.
     * @param username
     * @param request
     * @return new competition
     */
    public Competition createCompetition(NetId username, ActivityRequestModel request) {
        Competition competition = new Competition(username, request.getTimeSlot(), request.getBoat(), request.getPositions(), request.getOrganization(), request.getGender(), request.getCompetitive());
        activityRepository.save(competition);
        return competition;
    }

    /**
     * Deletes all activities of the given user.
     * @param netId
     * @param logged
     */
    public void deleteByUser(NetId netId, NetId logged) throws UnauthorizedException, ActivityNotFoundException {
        if(netId.getNetIdValue().equals(logged.getNetIdValue())) {
            List<Activity> toDelete = toDelete(netId);
            if (toDelete.isEmpty()) {
                throw new ActivityNotFoundException("No activities found for this user.");
            }
            activityRepository.deleteAll(toDelete);
        } else {
            throw new UnauthorizedException("You are not the owner of this activity.");
        }
    }

    public List<Activity> toDelete(NetId netId) {
        List<Activity> toDelete = new ArrayList<>();
        for(Activity activity : activityRepository.findAll()) {
            if (activity.getOwner().getNetIdValue().equals(netId.getNetIdValue())) {
                toDelete.add(activity);
            }
        }
        return toDelete;
    }

    public void deleteById(NetId netId, long id) throws UnauthorizedException, ActivityNotFoundException {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isPresent()) {
            if (activity.get().getOwner().getNetIdValue().equals(netId.getNetIdValue())) {
                activityRepository.deleteById(id);
            } else {
                throw new UnauthorizedException("You are not the owner of this activity.");
            }
        } else {
            throw new ActivityNotFoundException(id);
        }
    }
}
