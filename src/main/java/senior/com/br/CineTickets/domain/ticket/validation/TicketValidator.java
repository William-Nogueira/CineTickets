package senior.com.br.CineTickets.domain.ticket.validation;

import senior.com.br.CineTickets.domain.ticket.DTO.PostTicketDTO;

public interface TicketValidator {
    void validate(PostTicketDTO dto);
}
