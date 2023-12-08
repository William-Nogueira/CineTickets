package senior.com.br.CineTickets.validation.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senior.com.br.CineTickets.domain.session.SessionEntity;
import senior.com.br.CineTickets.domain.session.SessionRepository;
import senior.com.br.CineTickets.domain.ticket.DTO.PostTicketDTO;
import senior.com.br.CineTickets.domain.ticket.validation.TicketException;
import senior.com.br.CineTickets.domain.ticket.validation.ValidatorSessionIsActive;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

public class ValidatorSessionIsActiveTest {

    @InjectMocks
    private ValidatorSessionIsActive validatorSessionIsActive;

    @Mock
    private SessionRepository sessionRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenSessionIsActive_thenNoExceptionIsThrown() {
        PostTicketDTO postTicketDto = new PostTicketDTO("Person 1", 1L);
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setStartTime(LocalDateTime.now().plusHours(1));
        when(sessionRepository.findById(postTicketDto.session_id())).thenReturn(java.util.Optional.of(sessionEntity));

        validatorSessionIsActive.validate(postTicketDto);
    }

    @Test
    public void whenSessionIsNotActive_thenExceptionIsThrown() {
        PostTicketDTO postTicketDto = new PostTicketDTO("Person 1", 1L);
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setStartTime(LocalDateTime.now().minusHours(1));
        when(sessionRepository.findById(postTicketDto.session_id())).thenReturn(java.util.Optional.of(sessionEntity));

        assertThrows(TicketException.class, () -> validatorSessionIsActive.validate(postTicketDto));
    }
}

