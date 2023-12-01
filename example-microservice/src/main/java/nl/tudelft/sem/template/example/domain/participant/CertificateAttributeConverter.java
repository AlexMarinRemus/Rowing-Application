package nl.tudelft.sem.template.example.domain.participant;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CertificateAttributeConverter implements AttributeConverter<Certificate, String> {

    /**
     * Converts a Certificate to a String.
     *
     * @param certificate the certificate to convert
     * @return the string representation of the certificate
     */
    @Override
    public String convertToDatabaseColumn(Certificate certificate){return certificate.toString();}

    /**
     * Converts a String to a Certificate.
     * @param dbData
     * @return the certificate
     */
    @Override
    public Certificate convertToEntityAttribute(String dbData){return new Certificate(dbData);}

}