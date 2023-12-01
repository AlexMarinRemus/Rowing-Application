package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NetIdAttributeConverterTest {

    @Test
    void convertToDatabaseColumn() {
        NetIdAttributeConverter nac= new NetIdAttributeConverter();
        String generatedString = new Random().ints(97, 123)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        NetId netId= new NetId(generatedString);
        assertTrue(nac.convertToDatabaseColumn(netId).equals(netId.toString()));
    }

    @Test
    void convertToEntityAttribute() {
        NetIdAttributeConverter nac= new NetIdAttributeConverter();
        String generatedString = new Random().ints(97, 123)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        NetId netId= new NetId(generatedString);
        assertTrue(nac.convertToEntityAttribute(generatedString).equals(netId));
    }
}