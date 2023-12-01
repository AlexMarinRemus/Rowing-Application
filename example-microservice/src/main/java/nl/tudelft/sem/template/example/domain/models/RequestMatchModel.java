package nl.tudelft.sem.template.example.domain.models;

import lombok.Data;

import java.util.List;

/**
 * model representing a request model
 */
@Data
public class RequestMatchModel {
    private List<String> timeslots;

}
