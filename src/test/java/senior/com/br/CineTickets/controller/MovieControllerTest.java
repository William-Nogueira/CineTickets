package senior.com.br.CineTickets.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.data.domain.Pageable;

import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;
import senior.com.br.CineTickets.domain.movie.MovieEntity;
import senior.com.br.CineTickets.domain.movie.MovieService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MovieControllerTest {

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostMovie() {
        // Arrange
        PostMovieDTO postMovieDTO = new PostMovieDTO("Movie Title", "Action", "Director", 120);
        GetMovieDTO createdMovieDTO = new GetMovieDTO(1L, "Movie Title", "Action", "Director", 120);

        when(movieService.createMovie(any())).thenReturn(createdMovieDTO);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act
        ResponseEntity<GetMovieDTO> response = movieController.postMovie(postMovieDTO, uriBuilder);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdMovieDTO.id(), response.getBody().id());
        assertEquals(createdMovieDTO.title(), response.getBody().title());
        assertEquals(createdMovieDTO.genre(), response.getBody().genre());
        assertEquals(createdMovieDTO.director(), response.getBody().director());
        assertEquals(createdMovieDTO.duration(), response.getBody().duration());
    }

    @Test
    public void testListActiveMovies() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        MovieEntity movie1 = new MovieEntity();
        movie1.setId(1L);
        movie1.setTitle("Movie 1");

        MovieEntity movie2 = new MovieEntity();
        movie2.setId(2L);
        movie2.setTitle("Movie 2");

        List<MovieEntity> movieList = Arrays.asList(movie1, movie2);

        List<GetMovieDTO> getMovieDtoList = movieList.stream()
                .map(GetMovieDTO::new)
                .collect(Collectors.toList());
        Page<GetMovieDTO> getMovieDtoPage = new PageImpl<>(getMovieDtoList);

        when(movieService.listActiveMovies(pageable)).thenReturn(getMovieDtoPage);

        // Act
        ResponseEntity<Page<GetMovieDTO>> response = movieController.listActiveMovies(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getTotalElements());
        assertEquals("Movie 1", response.getBody().getContent().get(0).title());
        assertEquals("Movie 2", response.getBody().getContent().get(1).title());
    }

    @Test
    public void testGetMovieById() {
        // Arrange
        Long id = 1L;
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(id);
        movieEntity.setTitle("Movie 1");

        GetMovieDTO getMovieDto = new GetMovieDTO(movieEntity);

        when(movieService.getMovieById(id)).thenReturn(getMovieDto);

        // Act
        ResponseEntity<GetMovieDTO> response = movieController.getMovieById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movie 1", Objects.requireNonNull(response.getBody()).title());
    }

    @Test
    public void testUpdateMovie() {
        // Arrange
        Long id = 1L;
        GetMovieDTO getMovieDto = new GetMovieDTO(1L, "Updated Movie", "Genre 1", "Director 1", 120);

        when(movieService.updateMovieInfo(id, getMovieDto)).thenReturn(getMovieDto);

        // Act
        ResponseEntity<GetMovieDTO> response = movieController.updateMovie(id, getMovieDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Movie", Objects.requireNonNull(response.getBody()).title());
    }

    @Test
    public void testRemoveMovie() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = movieController.removeMovie(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testSearchMoviesByTitleOrGenre() {
        // Arrange
        String title = "Movie";
        String genre = "Genre";
        Pageable pageable = PageRequest.of(0, 10);

        MovieEntity movie1 = new MovieEntity();
        movie1.setId(1L);
        movie1.setTitle("Movie 1");

        MovieEntity movie2 = new MovieEntity();
        movie2.setId(2L);
        movie2.setTitle("Movie 2");

        List<MovieEntity> movieList = Arrays.asList(movie1, movie2);

        List<GetMovieDTO> getMovieDtoList = movieList.stream()
                .map(GetMovieDTO::new)
                .collect(Collectors.toList());
        Page<GetMovieDTO> getMovieDtoPage = new PageImpl<>(getMovieDtoList);

        when(movieService.searchMoviesByTitleOrGenre(title, genre, pageable)).thenReturn(getMovieDtoPage);

        // Act
        ResponseEntity<Page<GetMovieDTO>> response = movieController.searchMoviesByTitleOrGenre(title, genre, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getTotalElements());
        assertEquals("Movie 1", response.getBody().getContent().get(0).title());
        assertEquals("Movie 2", response.getBody().getContent().get(1).title());
    }


}
