package nl.tudelft.sem.template.example.domain.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * repository for participants
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {

    /**
     * Find user by NetID.
     */
    Optional<Participant> findByNetId(NetId netId);

    /**
     * checks if there is already an user with this name
     */
    //boolean existsByUsername(Username username);

}
