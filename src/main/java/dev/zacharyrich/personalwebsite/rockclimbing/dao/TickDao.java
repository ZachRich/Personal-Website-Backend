package dev.zacharyrich.personalwebsite.rockclimbing.dao;

import dev.zacharyrich.personalwebsite.rockclimbing.model.Tick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TickDao extends JpaRepository<Tick, Long> {
    void deleteTickById(Long id);

    Optional<Tick> findTickById(Long id);

    boolean existsByBoulderNameAndGradeAndRouteNameAndSendDate(String boulderName, String grade, String routeName, Date sendDate);

    Tick findByBoulderNameAndGradeAndRouteNameAndSendDate(String boulderName, String grade, String routeName, Date sendDate);
}
