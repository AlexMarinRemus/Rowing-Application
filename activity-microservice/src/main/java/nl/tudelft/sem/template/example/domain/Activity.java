package nl.tudelft.sem.template.example.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "activities")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Training.class, name = "Training"),

        @JsonSubTypes.Type(value = Competition.class, name = "Competition") }
)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public abstract class Activity {

    /**
     * The id of the activity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable=false)
    private long id;

    /**
     * The owner of the activity.
     */
    @Column(name = "owner", nullable = false)
    @Convert(converter = NetIdAttributeConverter.class)
    private NetId owner;

    /**
     * The time slot of the activity.
     */
    @Column(name= "timeSlot", nullable = false)
    @Convert(converter = TimeSlotConverter.class)
    private TimeSlot timeSlot;

    /**
     * The required boat for the activity.
     */
    @Column(name = "boat", nullable = false)
    private String boat;

    /**
     * The required positions for the activity.
     */
    @Column(name = "positions", nullable = false)
    @Convert(converter = PositionListConverter.class)
    private List<String> positions;

    /**
     * The constructor for the activity.
     * @param owner
     * @param timeSlot
     * @param boat
     * @param positions
     */
    public Activity(NetId owner, TimeSlot timeSlot, String boat, List<String> positions) {
        this.owner = owner;
        this.timeSlot = timeSlot;
        this.boat = boat;
        this.positions = positions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}
