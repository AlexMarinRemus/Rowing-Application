package nl.tudelft.sem.template.authentication.domain.user;

import lombok.EqualsAndHashCode;

import java.util.regex.Pattern;

/**
 * A DDD value object representing a NetID in our domain.
 */
@EqualsAndHashCode
public class NetId {
    private final transient String netIdValue;

    private final transient Pattern pattern = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");

    /**
     * Constructor for NetId.
     * @param netId
     */
    public NetId(String netId) {
        // validate NetID
        this.netIdValue = netId;
    }

    /**
     * Checks if the NetID is valid.
     * @return true if the NetID is valid, false otherwise
     */
    public boolean isValid(){
        return (pattern.matcher(netIdValue).matches()) && (netIdValue.length() < 30) && (netIdValue != null);
    }

    /**
     *
     * @return netIdValue
     */
    @Override
    public String toString() {
        return netIdValue;
    }
}
