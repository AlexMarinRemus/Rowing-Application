package nl.tudelft.sem.template.example.domain;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CertificateAttributeConverter implements AttributeConverter<Certificate, String> {

    /**
     * Converts a Certificate to a String.
     * @param certificate
     * @return
     */
    @Override
    public String convertToDatabaseColumn(Certificate certificate){return certificate.toString();}

    /**
     * Converts a String to a Certificate.
     * @param dbData
     * @return
     */
    @Override
    public Certificate convertToEntityAttribute(String dbData){return new Certificate(dbData);}

}