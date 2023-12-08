package senior.com.br.CineTickets.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.room.DTO.GetRoomDTO;
import senior.com.br.CineTickets.domain.room.DTO.PostRoomDTO;
import senior.com.br.CineTickets.domain.room.RoomEntity;
import senior.com.br.CineTickets.domain.room.RoomService;

import java.util.Objects;

public class RoomControllerTest {

    @InjectMocks
    private RoomController roomController;

    @Mock
    private RoomService roomService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostRoom() {
        // Arrange
        PostRoomDTO postRoomDto = new PostRoomDTO("Room 1", 100);
        RoomEntity roomEntity = new RoomEntity(postRoomDto);
        roomEntity.setId(1L);
        GetRoomDTO getRoomDto = new GetRoomDTO(roomEntity);

        Mockito.when(roomService.createRoom(postRoomDto)).thenReturn(getRoomDto);

        // Act
        ResponseEntity<GetRoomDTO> response = roomController.postRoom(postRoomDto, UriComponentsBuilder.newInstance());

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Room 1", Objects.requireNonNull(response.getBody()).name());
        assertEquals(100, response.getBody().numberSeats());
    }

}