package nl.tudelft.sem.template.example.domain.utils;

import nl.tudelft.sem.template.example.domain.Activity;
import nl.tudelft.sem.template.example.domain.Competition;
import nl.tudelft.sem.template.example.domain.Training;
import nl.tudelft.sem.template.example.domain.transferObject.TransferMatch;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Entity;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
public class ServerUtils {
    transient String ACTIVITY_SERVER = new String("http://localhost:8084/activity/");

    transient String credentials="Bearer ";

    /**
     * Gets all the activities from the activity server.
     * @return list of activities
     */
    public List<Activity> getActivities(){
        try {
            return new ResteasyClientBuilder().build()
                    .target(ACTIVITY_SERVER).path("all")
                    .request(APPLICATION_JSON)
                    .header(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, credentials+ SecurityContextHolder.getContext().getAuthentication().getCredentials())
                    .accept(APPLICATION_JSON)
                    .get(List.class);
        } catch(Exception e){
            System.out.println("Activities not found");
        }
        return new ArrayList<>();
    }

    /**
     * Gets all training activities from the activity server.
     * @return list of training activities
     */
    public List<Training> getTrainings() {
        try {
            return new ResteasyClientBuilder().build()
                    .target(ACTIVITY_SERVER).path("training")
                    .request(APPLICATION_JSON)
                    .header(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, credentials+ SecurityContextHolder.getContext().getAuthentication().getCredentials())
                    .accept(APPLICATION_JSON)
                    .get(List.class);
        } catch(Exception e){
            System.out.println("Trainings not found");
        }
        return new ArrayList<>();
    }

    /**
     * Gets all competition activities from the activity server.
     * @return list of competition activities
     */
    public List<Competition> getCompetitions() {
        try {
            return new ResteasyClientBuilder().build()
                    .target(ACTIVITY_SERVER).path("competition")
                    .request(APPLICATION_JSON)
                    .header(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, credentials+ SecurityContextHolder.getContext().getAuthentication().getCredentials())
                    .accept(APPLICATION_JSON)
                    .get(List.class);
        } catch(Exception e){
            System.out.println("Competitions not found");
        }
        return new ArrayList<>();
    }

    transient String NOTIFICATION_SERVER = new String("http://localhost:8085/");

    /**
     * Sends a notification to the notification server.
     * @param tm
     * @return response entity
     */
    public ResponseEntity<String> sendPendingUser(TransferMatch tm){
        try{
            new ResteasyClientBuilder().build()
                    .target(NOTIFICATION_SERVER).path("notifications/createOwnerNotification")
                    .request(APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, credentials+ SecurityContextHolder.getContext().getAuthentication().getCredentials())
                    .accept(APPLICATION_JSON)
                    .post(Entity.entity(tm,APPLICATION_JSON));
            return ResponseEntity.ok("sent");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("not sent");
    }

    /**
     * Sends a notification to the notification server.
     * @param acceptedMatches
     * @return response entity
     */
    public ResponseEntity<String> sendAcceptedUsers(List<TransferMatch> acceptedMatches) {
        try{
            new ResteasyClientBuilder().build()
                    .target(NOTIFICATION_SERVER).path("notifications/createParticipantNotification")
                    .request(APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, credentials + SecurityContextHolder.getContext().getAuthentication().getCredentials())
                    .accept(APPLICATION_JSON)
                    .post(Entity.entity(acceptedMatches,APPLICATION_JSON));

            return ResponseEntity.ok("Accepted Participants - sent");

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("Accepted Participants - fail to send");
    }
}