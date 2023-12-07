package senior.com.br.CineTickets.domain.movie.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostMovieDTO(
        @NotBlank
        String title,
        String genre,
        String director,
        @NotNull
        int duration) {
}
