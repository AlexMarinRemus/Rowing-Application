package nl.tudelft.sem.template.authentication.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A DDD service for hashing passwords.
 */
public class PasswordHashingService {

    private final transient PasswordEncoder encoder;

    /**
     * Constructor for PasswordHashingService.
     * @param encoder
     */
    public PasswordHashingService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Hashes a password.
     * @param password
     * @return
     */
    public HashedPassword hash(Password password) {
        return new HashedPassword(encoder.encode(password.toString()));
    }
}
