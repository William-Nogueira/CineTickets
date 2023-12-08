package senior.com.br.CineTickets.domain.ticket.DTO;

import senior.com.br.CineTickets.domain.session.SessionEntity;
import senior.com.br.CineTickets.domain.ticket.TicketEntity;

import java.time.LocalDateTime;

public record GetTicketDTO(long id, String personName, LocalDateTime creationTime, SessionEntity session) {

    public GetTicketDTO(TicketEntity ticket) {
        this(ticket.getId(), ticket.getPersonName(), ticket.getCreationTime(), ticket.getSession());
    }

}
