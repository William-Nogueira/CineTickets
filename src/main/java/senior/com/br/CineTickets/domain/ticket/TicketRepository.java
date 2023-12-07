package senior.com.br.CineTickets.domain.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import senior.com.br.CineTickets.domain.seat.Seat;
import senior.com.br.CineTickets.domain.session.Session;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Session getSessionById(long l);
}
