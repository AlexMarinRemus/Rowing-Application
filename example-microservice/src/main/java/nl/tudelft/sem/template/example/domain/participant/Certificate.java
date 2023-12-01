package nl.tudelft.sem.template.example.domain.participant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * certificate of the Participant.
 */
@EqualsAndHashCode
@Getter
@Setter
public class Certificate {
    final transient String certificateType;

    /**
     * Constructor for the certificate.
     * @param certificateType
     */
    public Certificate(String certificateType){
        this.certificateType=certificateType;
    }


    /**
     * Checks if the certificate is valid.
     * @return true if the certificate is valid
     */
    boolean isValid() {
        ArrayList<String> strings=new ArrayList<String>(List.of(new String[]{"C4", "4+", "8+"}));
        if(strings.contains(certificateType))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return certificateType;
    }

    /**
     * Checks if the certificate is better than the other.
     * @param other
     * @return true if the certificate is better
     */
    boolean isBetterCertificate(Certificate other) {
        Map<String,Integer> hm = Map.of("C4",1, "4+", 2,"8+",3);

        return hm.get(this.getCertificateType())>=hm.get(other.getCertificateType());

    }

}
