package nl.tudelft.sem.template.example.domain.models;

import lombok.Data;

/**
 * model representing a request model
 */
@Data
public class ParticipationRequestModel {
    private String positions;
    private String certificate;
    private String gender;
    private String organization;
    private Boolean level;
}
