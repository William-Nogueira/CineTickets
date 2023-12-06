package senior.com.br.CineTickets.domain.session.DTO;

import senior.com.br.CineTickets.domain.movie.Movie;
import senior.com.br.CineTickets.domain.room.Room;
import senior.com.br.CineTickets.domain.session.Session;

import java.time.LocalDateTime;

public record GetSessionDTO(long id, Movie movie, Room room, LocalDateTime startTime) {

    public GetSessionDTO(Session session){
        this(session.getId(), session.getMovie(), session.getRoom(), session.getStartTime());
    }

}
