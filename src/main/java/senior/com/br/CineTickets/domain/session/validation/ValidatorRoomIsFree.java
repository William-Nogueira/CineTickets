package senior.com.br.CineTickets.domain.session.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senior.com.br.CineTickets.domain.movie.MovieRepository;
import senior.com.br.CineTickets.domain.room.Room;
import senior.com.br.CineTickets.domain.room.RoomRepository;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;
import senior.com.br.CineTickets.domain.session.Session;
import senior.com.br.CineTickets.domain.session.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class ValidatorRoomIsFree implements SessionValidator {

    @Autowired
    private SessionRepository sessionRepository;

    public void validate(PostSessionDTO dto) {
        LocalDateTime startTime = dto.startTime();
        LocalDateTime endTime = startTime.plusMinutes(dto.duration());

        List<Session> overlappingSessions = sessionRepository.findOverlappingSessions(dto.room_id(), startTime, endTime);

        if (!overlappingSessions.isEmpty()) {
            throw new SessionException("Another session is already scheduled in this room during the requested period.");
        }
    }
}

