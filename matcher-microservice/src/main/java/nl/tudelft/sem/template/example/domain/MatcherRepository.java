package nl.tudelft.sem.template.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatcherRepository extends JpaRepository<Match,Long> {

    Optional<Match> findByactivityId(Long activityId);
}
