package nl.tudelft.sem.template.example.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class NetId {
    private transient String id;

    /**
     * Constructor for NetId.
     * @param id
     */
    public NetId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public String toString(){
        return id;
    }
}
