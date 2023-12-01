package nl.tudelft.sem.template.example.domain.participant;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Positions that the participant is able to fill
 */
@EqualsAndHashCode
@Getter
public class PositionManager {

    private List<String> positions;


    /**
     * Constructor for the position manager.
     * @param positionsDB
     */
    public PositionManager(String positionsDB){

        String[] returnedPos = positionsDB.split(",");
        positions = new ArrayList<>();
        for(String s : returnedPos){
            positions.add(s);
        }

        this.positions = positions;
    }

    @Override
    public String toString() {
        String res = "";
        for(int i = 0; i< positions.size()-1; i++){
            res += positions.get(i)+",";
        }
        int x=1;
        if(positions.size()>=x) {
            res += positions.get(positions.size() - 1);
        }
        return res;
    }
}
