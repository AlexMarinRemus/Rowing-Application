package nl.tudelft.sem.template.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Competition extends Activity{
    private String organization;
    private String gender;
    private boolean competitive;

    /**
     * The constructor for the competition.
     * @param owner
     * @param timeSlot
     * @param boat
     * @param positions
     * @param organization
     * @param gender
     * @param competitive
     */
    public Competition(NetId owner, TimeSlot timeSlot, String boat, List<String> positions, String organization, String gender, boolean competitive) {
        super(owner, timeSlot, boat, positions);
        this.organization = organization;
        this.gender = gender;
        this.competitive = competitive;
    }

    public boolean getCompetitive() {
        return competitive;
    }

}
