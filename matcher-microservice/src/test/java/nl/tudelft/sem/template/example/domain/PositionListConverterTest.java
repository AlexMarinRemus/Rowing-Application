package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PositionListConverterTest {
    PositionListConverter positionListConverter;
    @BeforeEach
    void setup(){
        positionListConverter = new PositionListConverter();
    }
    @Test
    void convertToDatabaseColumnNull(){
        assertEquals("",positionListConverter.convertToDatabaseColumn(null));
    }
    @Test
    void convertToDatabaseColumnNotNull(){
        List<String> lst = List.of("cox","cox");
        assertEquals("cox,cox",positionListConverter.convertToDatabaseColumn(lst));
    }
    @Test
    void convertToEntityAttribute(){
        List<String> lst = List.of("cox","cox");
        assertEquals(lst,positionListConverter.convertToEntityAttribute("cox,cox"));

    }
    @Test
    void convertToEntityAttributeEmpty(){
        List<String> lst = new ArrayList<>();
        assertThat(positionListConverter.convertToEntityAttribute("").isEmpty());

    }


}