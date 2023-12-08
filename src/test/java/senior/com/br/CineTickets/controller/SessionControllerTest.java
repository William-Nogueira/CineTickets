package senior.com.br.CineTickets.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.movie.MovieEntity;
import senior.com.br.CineTickets.domain.room.RoomEntity;
import senior.com.br.CineTickets.domain.session.DTO.GetSessionDTO;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;
import senior.com.br.CineTickets.domain.session.SessionEntity;
import senior.com.br.CineTickets.domain.session.SessionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListActiveSessions() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        SessionEntity sessionEntity = new SessionEntity(new MovieEntity(), new RoomEntity(), LocalDateTime.now());
        sessionEntity.setId(1L);
        GetSessionDTO getSessionDto = new GetSessionDTO(sessionEntity);

        List<GetSessionDTO> getSessionDtoList = List.of(getSessionDto);
        Page<GetSessionDTO> getSessionDtoPage = new PageImpl<>(getSessionDtoList);

        Mockito.when(sessionService.listActiveSessions(pageable)).thenReturn(getSessionDtoPage);

        // Act
        ResponseEntity<Page<GetSessionDTO>> response = sessionController.listActiveSessions(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
    }

    @Test
    public void testSearchSessionsByMovieName() {
        // Arrange
        String movieName = "Movie 1";
        Pageable pageable = PageRequest.of(0, 10);
        SessionEntity sessionEntity = new SessionEntity(new MovieEntity(), new RoomEntity(), LocalDateTime.now());
        sessionEntity.setId(1L);
        GetSessionDTO getSessionDto = new GetSessionDTO(sessionEntity);

        List<GetSessionDTO> getSessionDtoList = List.of(getSessionDto);
        Page<GetSessionDTO> getSessionDtoPage = new PageImpl<>(getSessionDtoList);

        Mockito.when(sessionService.searchSessionsByMovieName(movieName, pageable)).thenReturn(getSessionDtoPage);

        // Act
        ResponseEntity<Page<GetSessionDTO>> response = sessionController.searchSessionsByMovieName(movieName, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
    }

    @Test
    public void testGetSessionById() {
        // Arrange
        Long id = 1L;
        SessionEntity sessionEntity = new SessionEntity(new MovieEntity(), new RoomEntity(), LocalDateTime.now());
        sessionEntity.setId(id);
        GetSessionDTO getSessionDto = new GetSessionDTO(sessionEntity);

        Mockito.when(sessionService.getSessionById(id)).thenReturn(getSessionDto);

        // Act
        ResponseEntity<GetSessionDTO> response = sessionController.getSessionById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testPostSession() {
        // Arrange
        PostSessionDTO postSessionDto = new PostSessionDTO(1L, 1L, LocalDateTime.now(), 100);
        SessionEntity sessionEntity = new SessionEntity(new MovieEntity(), new RoomEntity(), postSessionDto.startTime());
        sessionEntity.setId(1L);
        GetSessionDTO getSessionDto = new GetSessionDTO(sessionEntity);

        Mockito.when(sessionService.createSession(postSessionDto)).thenReturn(getSessionDto);

        // Act
        ResponseEntity<GetSessionDTO> response = sessionController.postSession(postSessionDto, UriComponentsBuilder.newInstance());

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testPostBatchSessions() {
        // Arrange
        PostSessionDTO postSessionDto = new PostSessionDTO(1L, 1L, LocalDateTime.now(), 100);
        SessionEntity sessionEntity = new SessionEntity(new MovieEntity(), new RoomEntity(), postSessionDto.startTime());
        sessionEntity.setId(1L);
        GetSessionDTO getSessionDto = new GetSessionDTO(sessionEntity);

        List<GetSessionDTO> getSessionDtoList = List.of(getSessionDto);

        Mockito.when(sessionService.createSessionsInDateRange(postSessionDto)).thenReturn(getSessionDtoList);

        // Act
        ResponseEntity<List<GetSessionDTO>> response = sessionController.postBatchSessions(postSessionDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testUpdateSession() {
        // Arrange
        Long id = 1L;
        PostSessionDTO postSessionDto = new PostSessionDTO(1L, 1L, LocalDateTime.now(), 100);
        SessionEntity sessionEntity = new SessionEntity(new MovieEntity(), new RoomEntity(), postSessionDto.startTime());
        sessionEntity.setId(id);
        GetSessionDTO getSessionDto = new GetSessionDTO(sessionEntity);

        Mockito.when(sessionService.updateSessionInfo(id, getSessionDto)).thenReturn(getSessionDto);

        // Act
        ResponseEntity<GetSessionDTO> response = sessionController.updateSession(id, getSessionDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveSession() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = sessionController.removeSession(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
