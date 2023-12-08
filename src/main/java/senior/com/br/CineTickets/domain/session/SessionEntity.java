package senior.com.br.CineTickets.domain.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import senior.com.br.CineTickets.domain.movie.MovieEntity;
import senior.com.br.CineTickets.domain.room.RoomEntity;

import java.time.LocalDateTime;

@Table(name = "session")
@Entity(name = "Session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="movie_id")
    private MovieEntity movie;
    @ManyToOne
    @JoinColumn(name="room_id")
    private RoomEntity room;
    private LocalDateTime startTime;
    private int duration;
    private int availableSeats;
    @JsonIgnore
    private boolean active;

    public SessionEntity(MovieEntity movieEntity, RoomEntity roomEntity, LocalDateTime startTime){
        this.movie = movieEntity;
        this.room = roomEntity;
        this.startTime = startTime;
        this.duration = movieEntity.getDuration() + 10;
        this.availableSeats = roomEntity.getNumberSeats();
        this.active = true;
    }

}

