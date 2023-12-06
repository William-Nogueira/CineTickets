package senior.com.br.CineTickets.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.Movie;
import senior.com.br.CineTickets.domain.movie.MovieRepository;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    MovieRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<GetMovieDTO> postMovie(@RequestBody @Valid PostMovieDTO dto, UriComponentsBuilder uriBuilder) {
        var movie = new Movie(dto);
        repository.save(movie);

        var uri = uriBuilder.path("/movie/{id}").buildAndExpand(movie.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetMovieDTO(movie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMovieDTO> getMovieById(@PathVariable Long id) {
        var movie = repository.getReferenceById(id);
        return ResponseEntity.ok(new GetMovieDTO(movie));
    }

}
