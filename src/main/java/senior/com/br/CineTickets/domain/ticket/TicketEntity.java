package senior.com.br.CineTickets.domain.ticket;

import jakarta.persistence.*;
import lombok.*;
import senior.com.br.CineTickets.domain.session.SessionEntity;


import java.time.LocalDateTime;

@Table(name = "ticket")
@Entity(name = "Ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personName;
    private LocalDateTime creationTime;
    @ManyToOne
    @JoinColumn(name="session_id")
    private SessionEntity session;

    public TicketEntity(String personName, SessionEntity session) {
        this.personName = personName;
        this.session = session;
        this.creationTime = LocalDateTime.now();
    }

}

