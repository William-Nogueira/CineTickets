package senior.com.br.CineTickets.domain.session;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import senior.com.br.CineTickets.domain.movie.MovieRepository;
import senior.com.br.CineTickets.domain.room.RoomRepository;
import senior.com.br.CineTickets.domain.session.DTO.GetSessionDTO;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;
import senior.com.br.CineTickets.domain.session.validation.SessionException;
import senior.com.br.CineTickets.domain.session.validation.SessionValidator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private List<SessionValidator> validator;

    @Transactional
    public GetSessionDTO createSession(PostSessionDTO dto) {
        var movie = movieRepository.findById(dto.movie_id())
                .orElseThrow(() -> new SessionException("Movie not found"));
        var room = roomRepository.findById(dto.room_id())
                .orElseThrow(() -> new SessionException("Room not found"));

        validator.forEach(v -> v.validate(dto));

        var session = new SessionEntity(movie, room, dto.startTime());
        sessionRepository.save(session);

        return new GetSessionDTO(session);
    }

    //Cria várias sessões para um dia
    @Transactional
    public List<GetSessionDTO> createSessionsInDateRange(PostSessionDTO dto) {

        List<GetSessionDTO> sessions = new ArrayList<>();

        var startTime = dto.startTime();
        var closingTime = LocalDateTime.of(startTime.toLocalDate(), LocalTime.of(22, 0));

        var movie = movieRepository.findById(dto.movie_id())
                .orElseThrow(() -> new SessionException("Movie not found"));

        var room = roomRepository.findById(dto.room_id())
                .orElseThrow(() -> new SessionException("Room not found"));

        validator.forEach(v -> v.validate(dto));

        while (startTime.isBefore(closingTime)) {
            var session = new SessionEntity(movie, room, startTime);
            sessionRepository.save(session);
            sessions.add(new GetSessionDTO(session));

            startTime = startTime.plusMinutes(dto.duration() + 20);
        }
        return sessions;
    }

    public GetSessionDTO getSessionById(Long id) {
        var session = sessionRepository.getReferenceById(id);
        return new GetSessionDTO(session);
    }

    public Page<GetSessionDTO> listActiveSessions(Pageable paging) {
        return sessionRepository.findAllByActiveTrue(paging).map(GetSessionDTO::new);
    }

    public Page<GetSessionDTO> searchSessionsByMovieName(String movieName, Pageable paging) {
        Page<SessionEntity> sessions = sessionRepository.findAllByMovieTitleContainingIgnoreCase(movieName, paging);
        return sessions.map(GetSessionDTO::new);
    }

    public GetSessionDTO updateSessionInfo(Long sessionId, GetSessionDTO dto) {
        SessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        if (dto.movie() != null) {
            session.setMovie(dto.movie());
        }
        if (dto.room() != null) {
            session.setRoom(dto.room());
        }
        if (dto.startTime() != null) {
            session.setStartTime(dto.startTime());
        }

        sessionRepository.save(session);
        return new GetSessionDTO(session);
    }

    @Transactional
    public void removeSession(Long id) {
        var session = sessionRepository.findById(id).orElseThrow(() -> new SessionException("Session not found"));
        session.setActive(false);
        sessionRepository.save(session);
    }


/*
    @Autowired
    private ScheduledExecutorService executorService;

    @Scheduled
    public void scheduleRemove(SessionEntity session) {
        Duration duration = Duration.ofMinutes(session.getDuration());
        Instant endTime = session.getStartTime().plus(duration).toInstant(ZoneOffset.UTC);
        long delay = Duration.between(Instant.now(), endTime).toMillis();
        executorService.schedule(session::remove, delay, TimeUnit.MILLISECONDS);
    }*/
}

