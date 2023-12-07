package senior.com.br.CineTickets.domain.session.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senior.com.br.CineTickets.domain.movie.MovieRepository;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;

@Component
public class ValidatorMovieIsAvailable implements SessionValidator {

    @Autowired
    private MovieRepository repository;

    public void validate(PostSessionDTO dto) {
        var movieIsActive = repository.findActiveById(dto.movie_id());
        if (!movieIsActive) {
            throw new SessionException("This movie is not available.");
        }
    }

}
