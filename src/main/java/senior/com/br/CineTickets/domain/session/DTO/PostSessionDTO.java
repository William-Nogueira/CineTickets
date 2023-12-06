package senior.com.br.CineTickets.domain.session.DTO;

import java.time.LocalDateTime;

public record PostSessionDTO(long movie_id, long room_id, LocalDateTime startTime) {
}
