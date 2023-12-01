package nl.tudelft.sem.template.example.domain;

import javax.persistence.AttributeConverter;

public class NetIdConverter implements AttributeConverter<NetId, String> {
    /**
     * Converts a NetId to a String.
     * @param netId
     * @return
     */
    @Override
    public String convertToDatabaseColumn(NetId netId){return netId.getId();}

    /**
     * Converts a String to a NetId.
     * @param dbData
     * @return
     */
    @Override
    public NetId convertToEntityAttribute(String dbData){return new NetId(dbData);}
}
