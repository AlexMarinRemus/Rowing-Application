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
public class MatcherEditService {
    private final transient MatcherRepository matcherRepository;
    private final transient ServerUtils serverUtils;

    /**
     * Constructor for MatcherService.
     * @param matcherRepository
     * @param serverUtils
     */
    public MatcherEditService(MatcherRepository matcherRepository, ServerUtils serverUtils) {
        this.matcherRepository = matcherRepository;
        this.serverUtils = serverUtils;
    }


    /**
     * Method to save a match.
     * @param m
     */
    public void saveMatch(Match m) {
        matcherRepository.save(m);
    }

    /**
     * Method to get all matches.
     * @return list of matches
     */
    public List<Match> getAllMatches() {
        return matcherRepository.findAll();
    }

    transient List<Match> matches;

    /**
     * Method to remove matches.
     * @param acceptedMatches
     */
    public void removeMatches(List<TransferMatch> acceptedMatches) {
        matches= getAllMatches();
        for(TransferMatch transferMatch: acceptedMatches){
            List<Match> toBeDeleted= findMatch(transferMatch,matches);
            for(Match m: toBeDeleted)
                deleteMatch(m);
        }
    }

    /**
     * Method to find a match.
     * @param tr
     * @param matches
     * @return list of matches
     */
    public List<Match> findMatch(TransferMatch tr,List<Match> matches){
        List<Match> toDeletMatches= new ArrayList<>();
        for(Match m : matches){
            if(m.getActivityId().equals(tr.getActivityId()))
                if(!toDeletMatches.contains(m))
                    toDeletMatches.add(m);
        }
        return toDeletMatches;
    }

    /**
     * Method to delete a match.
     * @param m
     */
    public void deleteMatch(Match m){
        matcherRepository.delete(m);
    }

}
