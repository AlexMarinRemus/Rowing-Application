package nl.tudelft.sem.template.authentication.domain.user;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing a password in our domain.
 */
@EqualsAndHashCode
public class Password {
    private final transient String passwordValue;

    /**
     * Constructor for Password.
     * @param password
     */
    public Password(String password) {
        // Validate input
        this.passwordValue = password;
    }

    /**
     * Checks if the password is valid.
     * @return true if the password is valid, false otherwise
     */
    public boolean isValid(){
        return (passwordValue.length() > 5) && (passwordValue != null);
    }

    /**
     *
     * @return passwordValue
     */
    @Override
    public String toString() {
        return passwordValue;
    }
}
