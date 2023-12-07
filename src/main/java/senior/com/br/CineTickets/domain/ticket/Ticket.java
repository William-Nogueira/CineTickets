package senior.com.br.CineTickets.domain.ticket;

import jakarta.persistence.*;
import lombok.*;
import senior.com.br.CineTickets.domain.seat.Seat;
import senior.com.br.CineTickets.domain.session.Session;
import senior.com.br.CineTickets.domain.ticket.DTO.PostTicketDTO;

import java.time.LocalDateTime;

@Table(name = "ticket")
@Entity(name = "Ticket")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personName;
    private LocalDateTime creationTime;
    @ManyToOne
    @JoinColumn(name="session_id")
    private Session session;

    public Ticket(String personName, Session session) {
        this.personName = personName;
        this.session = session;
        this.creationTime = LocalDateTime.now();
    }

}

