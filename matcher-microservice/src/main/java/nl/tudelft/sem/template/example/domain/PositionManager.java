package nl.tudelft.sem.template.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Positions that the participant is able to fill
 */
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class PositionManager {

    private List<String> positions;

    /**
     * Constructor for PositionManager.
     * @param positionsDB
     */
    public PositionManager(String positionsDB){

        String[] returnedPos = positionsDB.split(",");
        positions = new ArrayList<>();
        for(String s : returnedPos){
            positions.add(s);
        }

        this.positions=positions;
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
