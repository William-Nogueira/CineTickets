package senior.com.br.CineTickets.domain.Reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import senior.com.br.CineTickets.domain.Seat.Seat;
import senior.com.br.CineTickets.domain.session.Session;

@Table(name = "reservation")
@Entity(name = "Reservation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personName;
    @ManyToOne
    @JoinColumn(name="screening_id")
    private Session session;
    @ManyToOne
    @JoinColumn(name="seat_id")
    private Seat seat;
}

