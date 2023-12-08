package senior.com.br.CineTickets.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.session.SessionEntity;
import senior.com.br.CineTickets.domain.ticket.DTO.GetTicketDTO;
import senior.com.br.CineTickets.domain.ticket.DTO.PostTicketDTO;
import senior.com.br.CineTickets.domain.ticket.TicketEntity;
import senior.com.br.CineTickets.domain.ticket.TicketService;

import java.util.List;
import java.util.Objects;

public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNewTicket() {
        // Arrange
        PostTicketDTO postTicketDto = new PostTicketDTO("Person 1", 1L);
        TicketEntity ticketEntity = new TicketEntity(postTicketDto.personName(), new SessionEntity());
        ticketEntity.setId(1L);
        GetTicketDTO getTicketDto = new GetTicketDTO(ticketEntity);

        Mockito.when(ticketService.newTicket(postTicketDto)).thenReturn(getTicketDto);

        // Act
        ResponseEntity<GetTicketDTO> response = ticketController.newTicket(postTicketDto, UriComponentsBuilder.newInstance());

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person 1", Objects.requireNonNull(response.getBody()).personName());
    }

    @Test
    public void testListAllTickets() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        TicketEntity ticketEntity = new TicketEntity("Person 1", new SessionEntity());
        ticketEntity.setId(1L);
        GetTicketDTO getTicketDto = new GetTicketDTO(ticketEntity);

        List<GetTicketDTO> getTicketDtoList = List.of(getTicketDto);
        Page<GetTicketDTO> getTicketDtoPage = new PageImpl<>(getTicketDtoList);

        Mockito.when(ticketService.listAllTickets(pageable)).thenReturn(getTicketDtoPage);

        // Act
        ResponseEntity<Page<GetTicketDTO>> response = ticketController.listAllTickets(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getTotalElements());
        assertEquals("Person 1", response.getBody().getContent().get(0).personName());
    }

    @Test
    public void testGetTicketById() {
        // Arrange
        Long id = 1L;
        TicketEntity ticketEntity = new TicketEntity("Person 1", new SessionEntity());
        ticketEntity.setId(id);
        GetTicketDTO getTicketDto = new GetTicketDTO(ticketEntity);

        Mockito.when(ticketService.getTicketById(id)).thenReturn(getTicketDto);

        // Act
        ResponseEntity<GetTicketDTO> response = ticketController.getTicketById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person 1", Objects.requireNonNull(response.getBody()).personName());
    }


}

