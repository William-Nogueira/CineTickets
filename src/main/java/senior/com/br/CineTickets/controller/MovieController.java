package senior.com.br.CineTickets.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;
import senior.com.br.CineTickets.domain.movie.MovieService;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Mostra todos os filmes disponíveis com paginação
    @GetMapping
    public ResponseEntity<Page<GetMovieDTO>> listActiveMovies(@PageableDefault(size = 10, sort = {"title"}) Pageable paging) {
        var page = movieService.listActiveMovies(paging);
        return ResponseEntity.ok(page);
    }

    //Busca com filtro por título ou gênero de filmes, com paginação
    @GetMapping("/search")
    public ResponseEntity<Page<GetMovieDTO>> searchMoviesByTitleOrGenre(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @PageableDefault(size = 10, sort = {"title"}) Pageable paging) {
        var page = movieService.searchMoviesByTitleOrGenre(title, genre, paging);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMovieDTO> getMovieById(@PathVariable Long id) {
        var movieDto = movieService.getMovieById(id);
        return ResponseEntity.ok(movieDto);
    }

    @PostMapping
    public ResponseEntity<GetMovieDTO> postMovie(@RequestBody @Valid PostMovieDTO dto, UriComponentsBuilder uriBuilder) {
        var movieDto = movieService.createMovie(dto);
        var uri = uriBuilder.path("/movie/{id}").buildAndExpand(movieDto.id()).toUri();
        return ResponseEntity.created(uri).body(movieDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetMovieDTO> updateMovie(@PathVariable Long id, @RequestBody GetMovieDTO dto) {
        var movieDto = movieService.updateMovieInfo(id, dto);
        return ResponseEntity.ok(movieDto);
    }

    // Delete lógico, seta o filme como inativo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMovie(@PathVariable Long id) {
        movieService.removeMovie(id);
        return ResponseEntity.noContent().build();
    }
}
