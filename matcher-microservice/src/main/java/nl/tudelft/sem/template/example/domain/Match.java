package nl.tudelft.sem.template.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@Getter
@Setter
public class Match {

    @Id
    @SequenceGenerator(name = "match",
            sequenceName = "match",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "match")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name="participant", nullable = false)
    private String netId;

    @Column(name="activity", nullable = false)
    private Long activityId;

    @Column(name="position", nullable = false)
    private String position;

    /**
     * Constructor for Match.
     * @param netId
     * @param activityId
     * @param position
     */
    public Match(String netId, Long activityId,String position) {
        this.netId = netId;
        this.activityId = activityId;
        this.position=position;
    }
}
