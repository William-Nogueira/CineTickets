package senior.com.br.CineTickets.domain.room.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRoomDTO(
        @NotBlank
        String name,
        @NotNull
        int numberSeats) {
}
