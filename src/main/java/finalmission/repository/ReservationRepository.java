package finalmission.repository;

import finalmission.domain.Reservation;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByDate(@Param("date") LocalDate date);
}
