package senior.com.br.CineTickets.domain.movie;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;
import senior.com.br.CineTickets.domain.session.validation.SessionException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public GetMovieDTO createMovie(PostMovieDTO dto) {
        var movie = new MovieEntity(dto);
        movieRepository.save(movie);
        return new GetMovieDTO(movie);
    }

    public Page<GetMovieDTO> listActiveMovies(Pageable paging) {
        return movieRepository.findAllByActiveTrue(paging).map(GetMovieDTO::new);
    }

    public Page<GetMovieDTO> searchMoviesByTitleOrGenre(String title, String genre, Pageable paging) {
        var moviesByName = movieRepository.findAllByActiveTrueAndTitleContainingIgnoreCase(title, paging);
        var moviesByGenre = movieRepository.findAllByActiveTrueAndGenreContainingIgnoreCase(genre, paging);

        List<MovieEntity> allMovieEntities = new ArrayList<>();
        allMovieEntities.addAll(moviesByName.getContent());
        allMovieEntities.addAll(moviesByGenre.getContent());

        allMovieEntities = allMovieEntities.stream()
                .distinct()
                .collect(Collectors.toList());

        return new PageImpl<>(allMovieEntities.stream()
                .map(GetMovieDTO::new)
                .collect(Collectors.toList()), paging, allMovieEntities.size());
    }

    public GetMovieDTO getMovieById(Long id) {
        var movie = movieRepository.getReferenceById(id);
        return new GetMovieDTO(movie);
    }

    @Transactional
    public GetMovieDTO updateMovieInfo(Long movieId, GetMovieDTO dto) {
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        if (dto.title() != null) {
            movie.setTitle(dto.title());
        }
        if (dto.genre() != null) {
            movie.setGenre(dto.genre());
        }
        if (dto.director() != null) {
            movie.setDirector(dto.director());
        }
        if (dto.duration() != 0) {
            movie.setDuration(dto.duration());
        }

        movieRepository.save(movie);
        return new GetMovieDTO(movie);
    }

    @Transactional
    public void removeMovie(Long id) {
        var movie = movieRepository.findById(id).orElseThrow(() -> new SessionException("Session not found"));
        movie.setActive(false);
        movieRepository.save(movie);
    }
}

