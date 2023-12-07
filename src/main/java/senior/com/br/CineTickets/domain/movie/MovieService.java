package senior.com.br.CineTickets.domain.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public GetMovieDTO createMovie(PostMovieDTO dto) {
        var movie = new Movie(dto);
        movieRepository.save(movie);
        return new GetMovieDTO(movie);
    }

    public Page<GetMovieDTO> listActiveMovies(Pageable paging) {
        return movieRepository.findAllByActiveTrue(paging).map(GetMovieDTO::new);
    }

    public GetMovieDTO getMovieById(Long id) {
        var movie = movieRepository.getReferenceById(id);
        return new GetMovieDTO(movie);
    }

    @Transactional
    public GetMovieDTO updateMovie(GetMovieDTO dto) {
        var movie = movieRepository.getReferenceById(dto.id());
        movie.updateInfo(dto);
        return new GetMovieDTO(movie);
    }

    @Transactional
    public void removeMovie(Long id) {
        var movie = movieRepository.getReferenceById(id);
        movie.remove();
    }
}

