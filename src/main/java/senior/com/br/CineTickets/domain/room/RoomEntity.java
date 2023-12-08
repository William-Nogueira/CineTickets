package senior.com.br.CineTickets.domain.room;

import jakarta.persistence.*;
import lombok.*;
import senior.com.br.CineTickets.domain.room.DTO.PostRoomDTO;

@Table(name = "room")
@Entity(name = "Room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int numberSeats;

    public RoomEntity(PostRoomDTO dto) {
        this.name = dto.name();
        this.numberSeats = dto.numberSeats();
    }
}

