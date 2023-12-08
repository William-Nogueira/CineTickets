package senior.com.br.CineTickets.domain.session.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PostSessionDTO(
        @NotNull
        long movie_id,
        @NotNull
        long room_id,
        @NotNull
        LocalDateTime startTime,
        int duration
) {
}
