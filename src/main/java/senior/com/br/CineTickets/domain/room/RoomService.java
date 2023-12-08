package senior.com.br.CineTickets.domain.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senior.com.br.CineTickets.domain.movie.DTO.GetMovieDTO;
import senior.com.br.CineTickets.domain.movie.DTO.PostMovieDTO;
import senior.com.br.CineTickets.domain.movie.MovieEntity;
import senior.com.br.CineTickets.domain.room.DTO.GetRoomDTO;
import senior.com.br.CineTickets.domain.room.DTO.PostRoomDTO;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public GetRoomDTO createRoom(PostRoomDTO dto) {
        var room = new RoomEntity(dto);
        roomRepository.save(room);
        return new GetRoomDTO(room);
    }
}
