package senior.com.br.CineTickets.domain.ticket;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import senior.com.br.CineTickets.domain.seat.SeatRepository;
import senior.com.br.CineTickets.domain.session.SessionRepository;
import senior.com.br.CineTickets.domain.ticket.DTO.GetTicketDTO;
import senior.com.br.CineTickets.domain.ticket.DTO.PostTicketDTO;
import senior.com.br.CineTickets.domain.ticket.validation.TicketException;
import senior.com.br.CineTickets.domain.ticket.validation.TicketValidator;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private List<TicketValidator> validator;

    public GetTicketDTO newTicket(PostTicketDTO dto) {
        var session = sessionRepository.findById(dto.session_id())
                .orElseThrow(() -> new TicketException("Session not found"));

        int availableSeats = session.getAvailableSeats();
        if (availableSeats > 0) {
            session.setAvailableSeats(availableSeats - 1);
        } else {
            throw new TicketException("No available seats for this session");
        }

        var ticket = new Ticket(dto.personName(), session);
        ticketRepository.save(ticket);

        validator.forEach(v -> v.validate(dto));

        return new GetTicketDTO(ticket);
    }

    public GetTicketDTO getTicketById(Long id) {
        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        return new GetTicketDTO(ticket);
    }

    public Page<GetTicketDTO> listAllTickets(Pageable paging) {
        var ticketsPage = ticketRepository.findAll(paging)
                .map(GetTicketDTO::new);
        return ticketsPage;
    }

}

