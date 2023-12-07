package senior.com.br.CineTickets.domain.ticket.DTO;

import jakarta.validation.constraints.NotNull;

public record PostTicketDTO(
        String personName,
        @NotNull
        long session_id) {
}
