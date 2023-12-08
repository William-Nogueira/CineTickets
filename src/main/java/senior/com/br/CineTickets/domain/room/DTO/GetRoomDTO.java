package senior.com.br.CineTickets.domain.room.DTO;

import senior.com.br.CineTickets.domain.room.RoomEntity;

public record GetRoomDTO(long id, String name, int numberSeats) {

    public GetRoomDTO(RoomEntity room){
        this(room.getId(), room.getName(), room.getNumberSeats());
    }

}
