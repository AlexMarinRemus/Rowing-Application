package nl.tudelft.sem.template.example.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TimeSlotConverter implements AttributeConverter<TimeSlot, String> {

    /**
     * Converts a TimeSlot to a String.
     * @param timeSlot
     * @return
     */
    @Override
    public String convertToDatabaseColumn(TimeSlot timeSlot) {
        return timeSlot.toString();
    }

    /**
     * Converts a String to a TimeSlot.
     * @param dbData
     * @return
     */
    @Override
    public TimeSlot convertToEntityAttribute(String dbData) {
        return TimeSlot.getTimeSlot(dbData);
    }
}
