package senior.com.br.CineTickets.domain.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    Page<MovieEntity> findAllByActiveTrue(Pageable paging);

    @Query("""
            select m.active from Movie m
            where m.id = :id
            """)
    Boolean findActiveById(Long id);

    Page<MovieEntity> findAllByActiveTrueAndTitleContainingIgnoreCase(String name, Pageable paging);

    Page<MovieEntity> findAllByActiveTrueAndGenreContainingIgnoreCase(String genre, Pageable paging);
}
