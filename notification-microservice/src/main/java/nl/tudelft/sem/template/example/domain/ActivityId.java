package nl.tudelft.sem.template.example.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class ActivityId {
    private transient String id;

    /**
     * Constructor for ActivityId.
     * @param id
     */
    public ActivityId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return id;
    }
}
