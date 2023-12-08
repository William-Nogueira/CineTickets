package senior.com.br.CineTickets.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.session.DTO.GetSessionDTO;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;
import senior.com.br.CineTickets.domain.session.SessionService;

import java.util.List;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    // Mostra todas as sessões disponíveis com paginação
    @GetMapping
    public ResponseEntity<Page<GetSessionDTO>> listActiveSessions(@PageableDefault(size = 10, sort = {"startTime"}) Pageable paging) {
        var page = sessionService.listActiveSessions(paging);
        return ResponseEntity.ok(page);
    }

    //Busca com filtro por título do filme, com paginação
    @GetMapping("/search")
    public ResponseEntity<Page<GetSessionDTO>> searchSessionsByMovieName(
            @RequestParam() String movieName,
            @PageableDefault(size = 10, sort = {"startTime"}) Pageable paging) {
        var page = sessionService.searchSessionsByMovieName(movieName, paging);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSessionDTO> getSessionById(@PathVariable Long id) {
        var sessionDto = sessionService.getSessionById(id);
        return ResponseEntity.ok(sessionDto);
    }

    @PostMapping
    public ResponseEntity<GetSessionDTO> postSession(@RequestBody @Valid PostSessionDTO dto, UriComponentsBuilder uriBuilder) {
        var sessionDto = sessionService.createSession(dto);
        var uri = uriBuilder.path("/session/{id}").buildAndExpand(sessionDto.id()).toUri();
        return ResponseEntity.created(uri).body(sessionDto);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GetSessionDTO>> postBatchSessions(@RequestBody @Valid PostSessionDTO dto) {
        List<GetSessionDTO> sessions = sessionService.createSessionsInDateRange(dto);
        return ResponseEntity.ok(sessions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetSessionDTO> updateSession(@PathVariable Long id, @RequestBody @Valid GetSessionDTO dto) {
        var sessionDto = sessionService.updateSessionInfo(id, dto);
        return ResponseEntity.ok(sessionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSession(@PathVariable Long id) {
        sessionService.removeSession(id);
        return ResponseEntity.noContent().build();
    }

}

