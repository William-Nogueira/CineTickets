package senior.com.br.CineTickets.domain.movie.DTO;

import senior.com.br.CineTickets.domain.movie.Movie;

public record GetMovieDTO(long id, String title, String genre, String director, int duration) {

    public GetMovieDTO(Movie movie) {
        this(movie.getId(), movie.getTitle(), movie.getGenre(), movie.getDirector(), movie.getDuration());
    }

}
