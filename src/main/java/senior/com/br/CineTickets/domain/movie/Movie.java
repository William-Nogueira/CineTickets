package senior.com.br.CineTickets.domain.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
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
    @JsonIgnore
    private boolean active;

    public Movie(PostMovieDTO dto) {
        this.title = dto.title();
        this.genre = dto.genre();
        this.director = dto.director();
        this.duration = dto.duration();
        this.active = true;
    }

    public void updateInfo(GetMovieDTO dto) {
        if (dto.title() != null) {
            this.title = dto.title();
        } if (dto.genre() != null) {
            this.genre = dto.genre();
        } if (dto.director() != null) {
            this.director = dto.genre();
        } if (dto.duration() != 0) {
            this.duration = dto.duration();
        }
    }

    public void remove() {
        this.active = false;
    }
}

