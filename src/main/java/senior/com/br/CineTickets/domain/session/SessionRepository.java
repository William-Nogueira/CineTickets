package senior.com.br.CineTickets.domain.session;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    @Query("""
            SELECT s FROM Session s
            WHERE s.room.id = :roomId
            AND FUNCTION('TIMESTAMPADD', MINUTE, s.duration, s.startTime) > :startTime AND s.startTime < :endTime
            """)
    List<SessionEntity> findOverlappingSessions(Long roomId, LocalDateTime startTime, LocalDateTime endTime);

    Page<SessionEntity> findAllByActiveTrue(Pageable paging);

    @Query(value = "SELECT s FROM Session s JOIN s.movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :movieName, '%')) AND s.active = true",
            countQuery = "SELECT COUNT(s) FROM Session s JOIN s.movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :movieName, '%')) AND s.active = true")
    Page<SessionEntity> findAllByMovieTitleContainingIgnoreCase(String movieName, Pageable paging);
}