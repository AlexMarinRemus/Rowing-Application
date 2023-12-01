package nl.tudelft.sem.template.example.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityServiceGet {
    private final transient ActivityRepository activityRepository;

    /**
     * Constructor for ActivityService.
     * @param activityRepository the repository for activities
     */
    public ActivityServiceGet(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     *
     * @return all activities
     */
    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    /**
     * Gets all trainings.
     * @return all trainings
     */
    public List<Training> getTrainings() {
        List<Activity> activities = getAll();
        List<Training> trainings = new ArrayList<>();
        for(Activity activity : activities) {
            if(activity instanceof Training)
                trainings.add((Training) activity);
        }
        return trainings;
    }

    /**
     * Gets all competitions.
     * @return all competitions
     */
    public List<Competition> getCompetitions() {
        List<Activity> activities = getAll();
        List<Competition> competitions = new ArrayList<>();
        for(Activity activity : activities) {
            if(activity instanceof Competition)
                competitions.add((Competition) activity);
        }
        return competitions;
    }

    /**
     * Gets all activities of the given user.
     * @param username
     * @return all activties of the given user
     */
    public List<Activity> getByUsername(String username) throws ActivityNotFoundException {
        List<Activity> activities = getAll();
        List<Activity> result = new ArrayList<>();
        for(Activity activity : activities) {
            if(activity.getOwner().toString().equals(username))
                result.add(activity);
        }
        if (result.isEmpty()) {
            throw new ActivityNotFoundException("No activities found for this user.");
        }
        return result;
    }

    /**
     * Gets an activity with the given id.
     * @param id
     * @return the activity with the given id
     */
    public Activity getById(long id) throws ActivityNotFoundException {
        if (activityRepository.findById(id).isPresent()) {
            return activityRepository.findById(id).get();
        } else {
            throw new ActivityNotFoundException(id);
        }
    }
}
