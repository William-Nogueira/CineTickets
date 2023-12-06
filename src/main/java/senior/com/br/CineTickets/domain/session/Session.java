package senior.com.br.CineTickets.domain.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import senior.com.br.CineTickets.domain.movie.Movie;
import senior.com.br.CineTickets.domain.room.Room;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;

import java.time.LocalDateTime;

@Table(name = "session")
@Entity(name = "Session")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;
    private LocalDateTime startTime;

    public Session(Movie movie, Room room, LocalDateTime startTime){
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
    }

}

