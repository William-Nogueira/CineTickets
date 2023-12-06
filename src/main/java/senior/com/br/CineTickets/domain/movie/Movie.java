package senior.com.br.CineTickets.domain.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;

@Table(name = "movie")
@Entity(name = "Movie")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private String director;
    private int duration;

    public Movie(PostMovieDTO dto) {
        this.title = dto.title();
        this.genre = dto.genre();
        this.director = dto.director();
        this.duration = dto.duration();
    }
}

