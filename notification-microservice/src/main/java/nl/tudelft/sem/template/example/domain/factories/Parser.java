package nl.tudelft.sem.template.example.domain.factories;

import nl.tudelft.sem.template.example.domain.Notification;
import nl.tudelft.sem.template.example.domain.transferClasses.TransferMatch;

public interface Parser {
    TransferMatch parse(Notification notification);
    Notification parseOtherWay(TransferMatch transferMatch);
}
