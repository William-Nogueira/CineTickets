package senior.com.br.CineTickets.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.MovieRepository;
import senior.com.br.CineTickets.domain.room.RoomRepository;
import senior.com.br.CineTickets.domain.session.DTO.GetSessionDTO;
import senior.com.br.CineTickets.domain.session.Session;
import senior.com.br.CineTickets.domain.session.SessionRepository;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;
import senior.com.br.CineTickets.domain.session.SessionService;
import senior.com.br.CineTickets.domain.session.validation.SessionValidator;

import java.util.List;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<GetSessionDTO> postSession(@RequestBody @Valid PostSessionDTO dto, UriComponentsBuilder uriBuilder) {
        var sessionDto = sessionService.createSession(dto);
        var uri = uriBuilder.path("/session/{id}").buildAndExpand(sessionDto.id()).toUri();
        return ResponseEntity.created(uri).body(sessionDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSessionDTO> getSessionById(@PathVariable Long id) {
        var sessionDto = sessionService.getSessionById(id);
        return ResponseEntity.ok(sessionDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetSessionDTO>> listAllSessions(@PageableDefault(size = 10, sort = {"startTime"}) Pageable paging) {
        var page = sessionService.listAllSessions(paging);
        return ResponseEntity.ok(page);
    }
}

