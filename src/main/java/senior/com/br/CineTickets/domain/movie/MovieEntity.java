package senior.com.br.CineTickets.domain.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;

@Table(name = "movie")
@Entity(name = "Movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private String director;
    private int duration;
    @JsonIgnore
    private boolean active;

    public MovieEntity(PostMovieDTO dto) {
        this.title = dto.title();
        this.genre = dto.genre();
        this.director = dto.director();
        this.duration = dto.duration();
        this.active = true;
    }
}

