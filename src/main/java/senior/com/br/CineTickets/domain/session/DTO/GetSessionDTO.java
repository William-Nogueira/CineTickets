package senior.com.br.CineTickets.domain.session.DTO;

import senior.com.br.CineTickets.domain.movie.MovieEntity;
import senior.com.br.CineTickets.domain.room.RoomEntity;
import senior.com.br.CineTickets.domain.session.SessionEntity;

import java.time.LocalDateTime;

public record GetSessionDTO(long id, MovieEntity movie, RoomEntity room, LocalDateTime startTime, int availableSeats) {

    public GetSessionDTO(SessionEntity session){
        this(session.getId(), session.getMovie(), session.getRoom(), session.getStartTime(), session.getAvailableSeats());
    }

}
