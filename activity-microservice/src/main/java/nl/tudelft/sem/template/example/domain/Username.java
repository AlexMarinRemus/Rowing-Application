package nl.tudelft.sem.template.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * An object representing the username of the Participant
 */
@EqualsAndHashCode
@Getter
@Setter
public class Username {
    private final transient String usernameValue;

    /**
     * Constructor for Username.
     * @param usernameValue
     */
    public Username(String usernameValue){
        this.usernameValue=usernameValue;
    }

    @Override
    public String toString(){
        return usernameValue;
    }
}
