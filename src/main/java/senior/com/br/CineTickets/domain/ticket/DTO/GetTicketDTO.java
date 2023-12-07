package senior.com.br.CineTickets.domain.ticket.DTO;

import senior.com.br.CineTickets.domain.seat.Seat;
import senior.com.br.CineTickets.domain.session.Session;
import senior.com.br.CineTickets.domain.ticket.Ticket;

import java.time.LocalDateTime;

public record GetTicketDTO(long id, String personName, LocalDateTime creationTime, Session session) {

    public GetTicketDTO(Ticket ticket) {
        this(ticket.getId(), ticket.getPersonName(), ticket.getCreationTime(), ticket.getSession());
    }

}
