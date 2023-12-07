package senior.com.br.CineTickets.domain.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import senior.com.br.CineTickets.domain.movie.Movie;
import senior.com.br.CineTickets.domain.room.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("""
            SELECT s FROM Session s
            WHERE s.room.id = :roomId
            AND FUNCTION('TIMESTAMPADD', MINUTE, s.duration, s.startTime) > :startTime AND s.startTime < :endTime
            """)
    List<Session> findOverlappingSessions(Long roomId, LocalDateTime startTime, LocalDateTime endTime);
}



