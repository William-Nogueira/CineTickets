package senior.com.br.CineTickets.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.room.DTO.GetRoomDTO;
import senior.com.br.CineTickets.domain.room.DTO.PostRoomDTO;
import senior.com.br.CineTickets.domain.room.RoomService;

@RestController
@RequestMapping("room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<GetRoomDTO> postRoom(@RequestBody @Valid PostRoomDTO dto, UriComponentsBuilder uriBuilder) {
        var roomDto = roomService.createRoom(dto);
        var uri = uriBuilder.path("/room/{id}").buildAndExpand(roomDto.id()).toUri();
        return ResponseEntity.created(uri).body(roomDto);
    }

}
