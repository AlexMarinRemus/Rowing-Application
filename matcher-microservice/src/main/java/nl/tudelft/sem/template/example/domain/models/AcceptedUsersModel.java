package nl.tudelft.sem.template.example.domain.models;

import lombok.Data;
import nl.tudelft.sem.template.example.domain.transferObject.TransferMatch;

import java.util.List;

@Data
public class AcceptedUsersModel {
    List<TransferMatch> transferMatches;
}
