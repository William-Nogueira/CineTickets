package senior.com.br.CineTickets.domain.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findAllByActiveTrue(Pageable paging);

    @Query("""
            select m.active from Movie m
            where m.id = :id
            """)
    Boolean findActiveById(Long id);

    @Query("""
            SELECT m.duration FROM Movie m
            WHERE m.id = :id
            """)
    int findDurationById(Long id);

}
