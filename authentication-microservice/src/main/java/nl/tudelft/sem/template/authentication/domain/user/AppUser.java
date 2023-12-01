package nl.tudelft.sem.template.authentication.domain.user;

import java.util.Objects;
import javax.persistence.*;

import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.authentication.domain.HasEvents;

/**
 * A DDD entity representing an application user in our domain.
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
public class AppUser extends HasEvents {
    /**
     * Identifier for the application user.
     */
    @SequenceGenerator(name = "app_user",
            sequenceName = "app_user",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "app_user")
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "net_id", nullable = false, unique = true)
    @Convert(converter = NetIdAttributeConverter.class)
    private NetId netId;

    @Column(name = "password_hash", nullable = false)
    @Convert(converter = HashedPasswordAttributeConverter.class)
    private HashedPassword password;

    /**
     * Create new application user.
     *
     * @param netId The NetId for the new user
     * @param password The password for the new user
     */
    public AppUser(NetId netId, HashedPassword password) {
        this.netId = netId;
        this.password = password;
        this.recordThat(new UserWasCreatedEvent(netId));
    }

    /**
     * Changes the password of the user.
     * @param password
     */
    public void changePassword(HashedPassword password) {
        this.password = password;
        this.recordThat(new PasswordWasChangedEvent(this));
    }

    /**
     * Returns the NetId of the user.
     * @return
     */
    public NetId getNetId() {
        return netId;
    }

    /**
     * Returns the password of the user.
     * @return
     */
    public HashedPassword getPassword() {
        return password;
    }

    /**
     * Equality is only based on the identifier.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppUser appUser = (AppUser) o;
        return id == (appUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId);
    }
}
