package nl.tudelft.sem.template.example.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityServiceEdit {
    private final transient ActivityRepository activityRepository;

    /**
     * Constructor for ActivityService.
     * @param activityRepository the repository for activities
     */
    public ActivityServiceEdit(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Edits an activity.
     * @param id
     * @param request
     */
    public ResponseEntity editActivity(NetId netId, long id, ActivityRequestModel request) throws UnauthorizedException, ActivityNotFoundException {
        if(!activityRepository.existsById(id)) throw new ActivityNotFoundException("Activity not found");

        Activity change = activityRepository.findById(id).get();
        if (!change.getOwner().getNetIdValue().equals(netId.getNetIdValue())) throw new UnauthorizedException("Unauthorized");

        editTimeSlot(change, request);
        editBoat(change, request);
        editPositions(change, request);
        if(change instanceof Competition) {
            editCompetition((Competition) change, request);
        }
        activityRepository.save(change);
        return ResponseEntity.ok("successfully edited the activity");
    }

    /**
     * Edits the timeslot.
     * @param activity
     * @param request
     */
    public void editTimeSlot(Activity activity, ActivityRequestModel request) {
        if(!isNullOrEmpty(request.getTimeSlot())) {
            activity.setTimeSlot(request.getTimeSlot());
        }
    }

    /**
     * Edits the boat.
     * @param activity
     * @param request
     */
    public void editBoat(Activity activity, ActivityRequestModel request) {
        if(!isNullOrEmpty(request.getBoat())) {
            activity.setBoat(request.getBoat());
        }
    }

    /**
     * Edits positions.
     * @param activity
     * @param request
     */
    public void editPositions(Activity activity, ActivityRequestModel request) {
        if(!isNullOrEmpty(request.getPositions())) {
            activity.setPositions(request.getPositions());
        }
    }

    /**
     * Edits the fields of a competition.
     * @param competition
     * @param request
     */
    public void editCompetition(Competition competition, ActivityRequestModel request) {
        if (!isNullOrEmpty(request.getOrganization())) {
            (competition).setOrganization(request.getOrganization());
        }
        if (!isNullOrEmpty(request.getGender())) {
            (competition).setGender(request.getGender());
        }
        if (!isNullOrEmpty(request.getCompetitive())) {
            (competition).setCompetitive(request.getCompetitive());
        }
    }

    /**
     * Checks if a string is null or empty.
     * @param o
     * @return true if the string is null or empty
     */
    private static boolean isNullOrEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            String s = (String) o;
            return s.isEmpty();
        }
        return false;
    }
}
