package nl.tudelft.sem.template.authentication.domain.user;

/**
 * A DDD domain event that indicated a user was created.
 */
public class UserWasCreatedEvent {
    private final NetId netId;

    /**
     * Constructor for UserWasCreatedEvent.
     * @param netId
     */
    public UserWasCreatedEvent(NetId netId) {
        this.netId = netId;
    }

    /**
     * Gets the netId.
     * @return netId
     */
    public NetId getNetId() {
        return this.netId;
    }
}
