package senior.com.br.CineTickets.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.movie.MovieRepository;
import senior.com.br.CineTickets.domain.room.RoomRepository;
import senior.com.br.CineTickets.domain.session.DTO.GetSessionDTO;
import senior.com.br.CineTickets.domain.session.Session;
import senior.com.br.CineTickets.domain.session.SessionRepository;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RoomRepository roomRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<GetSessionDTO> postSession(@RequestBody @Valid PostSessionDTO dto, UriComponentsBuilder uriBuilder) {
        var movie = movieRepository.findById(dto.movie_id()).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        var room = roomRepository.findById(dto.room_id()).orElseThrow(() -> new IllegalArgumentException("Room not found"));

        var session = new Session(movie, room, dto.startTime());
        sessionRepository.save(session);

        var uri = uriBuilder.path("/session/{id}").buildAndExpand(session.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetSessionDTO(session));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSessionDTO> getSessionById(@PathVariable Long id) {
        var session = sessionRepository.getReferenceById(id);
        return ResponseEntity.ok(new GetSessionDTO(session));
    }

}
