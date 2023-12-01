package nl.tudelft.sem.template.example.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActivityIdConverter implements AttributeConverter<ActivityId, String> {

    /**
     * Converts an ActivityId to a String.
     * @param activityID
     * @return
     */
    @Override
    public String convertToDatabaseColumn(ActivityId activityID){return activityID.getId();}

    /**
     * Converts a String to an ActivityId.
     * @param dbData
     * @return
     */
    @Override
    public ActivityId convertToEntityAttribute(String dbData){return new ActivityId(dbData);}
}
