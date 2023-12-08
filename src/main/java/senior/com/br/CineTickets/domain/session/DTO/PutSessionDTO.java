package senior.com.br.CineTickets.domain.session.DTO;

import senior.com.br.CineTickets.domain.session.SessionEntity;

import java.time.LocalDateTime;

public record PutSessionDTO(long id, long movie_id, long room_id, LocalDateTime startTime) {

    public PutSessionDTO(SessionEntity session) {
        this(session.getId(), session.getMovie().getId(), session.getRoom().getId(), session.getStartTime());
    }

}

