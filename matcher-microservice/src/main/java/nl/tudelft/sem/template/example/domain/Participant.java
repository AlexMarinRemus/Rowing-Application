package nl.tudelft.sem.template.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
public class Participant  {

    @Column(name= "username", nullable = false)
    @Convert(converter = NetIdAttributeConverter.class)
    private NetId netId;

    @Column(name="positions", nullable = false)
    @Convert(converter = PositionAttributeCoverter.class)
    private PositionManager positionManager;

    @Column(name="gender", nullable = false)
    private String gender;

    @Column(name="certificate")
    @Convert(converter = CertificateAttributeConverter.class)
    private Certificate certificate;

    @Column(name = "organization")
    private String organization;

    @Column(name = "level")
    private Boolean level;

    /**
     * Constructor for Participant.
     * @param netId
     * @param positionManager
     * @param gender
     * @param certificate
     * @param organization
     * @param level
     */
    public Participant(NetId netId, PositionManager positionManager, String gender, Certificate certificate, String organization, Boolean level){
        this.netId= netId;
        this.positionManager= positionManager;
        this.gender=gender;
        this.certificate= certificate;
        this.organization= organization;
        this.level=level;
    }
}
