package nl.tudelft.sem.template.authentication.domain.user;

/**
 * A DDD domain event indicating a password had changed.
 */
public class PasswordWasChangedEvent {
    private final AppUser user;

    /**
     * Constructor for PasswordWasChangedEvent.
     * @param user
     */
    public PasswordWasChangedEvent(AppUser user) {
        this.user = user;
    }

    /**
     * Gets the user.
     * @return user
     */
    public AppUser getUser() {
        return this.user;
    }
}
