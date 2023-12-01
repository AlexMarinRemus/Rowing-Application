package nl.tudelft.sem.template.example.domain.participant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PositionAttributeCoverter implements AttributeConverter<PositionManager, String> {

    /**
     * Converts a PositionManager to a String.
     * @param pm
     * @return
     */
    @Override
    public String convertToDatabaseColumn(PositionManager pm){return pm.toString();}

    /**
     * Converts a String to a PositionManager.
     * @param dbData
     * @return
     */
    @Override
    public PositionManager convertToEntityAttribute(String dbData){return new PositionManager(dbData);}

}