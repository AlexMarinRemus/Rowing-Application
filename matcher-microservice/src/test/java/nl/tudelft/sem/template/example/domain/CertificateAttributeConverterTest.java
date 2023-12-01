package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateAttributeConverterTest {

    @Test
    void convertToDatabaseColumn() {
        CertificateAttributeConverter certificateAttributeConverter= new CertificateAttributeConverter();
        List<String> certificates= Arrays.asList("C4", "4+", "8+");
        for(String certificate : certificates) {
            Certificate c = new Certificate(certificate);
            assertTrue(certificateAttributeConverter.convertToDatabaseColumn(c).equals(certificate));
        }
    }

    @Test
    void convertToEntityAttribute() {
        CertificateAttributeConverter certificateAttributeConverter= new CertificateAttributeConverter();
        List<String> certificates= Arrays.asList("C4", "4+", "8+");
        for(String certificate : certificates) {
            Certificate c = new Certificate(certificate);
            assertTrue(certificateAttributeConverter.convertToEntityAttribute(certificate).equals(c));
        }
    }
}