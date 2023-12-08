package senior.com.br.CineTickets.domain.movie.DTO;

import senior.com.br.CineTickets.domain.movie.MovieEntity;

public record GetMovieDTO(long id, String title, String genre, String director, int duration) {

    public GetMovieDTO(MovieEntity movieEntity) {
        this(movieEntity.getId(), movieEntity.getTitle(), movieEntity.getGenre(), movieEntity.getDirector(), movieEntity.getDuration());
    }

}
