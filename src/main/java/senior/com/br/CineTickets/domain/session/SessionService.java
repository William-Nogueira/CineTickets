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
import senior.com.br.CineTickets.domain.session.validation.SessionValidator;

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
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        var room = roomRepository.findById(dto.room_id())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        var session = new Session(movie, room, dto.startTime());
        sessionRepository.save(session);

        validator.forEach(v -> v.validate(dto));

        return new GetSessionDTO(session);
    }

    public GetSessionDTO getSessionById(Long id) {
        var session = sessionRepository.getReferenceById(id);
        return new GetSessionDTO(session);
    }

    public Page<GetSessionDTO> listAllSessions(Pageable paging) {
        return sessionRepository.findAll(paging).map(GetSessionDTO::new);
    }
}

