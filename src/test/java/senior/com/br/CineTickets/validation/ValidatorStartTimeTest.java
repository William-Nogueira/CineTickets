package senior.com.br.CineTickets.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;
import senior.com.br.CineTickets.domain.session.validation.SessionException;
import senior.com.br.CineTickets.domain.session.validation.ValidatorStartTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

public class ValidatorStartTimeTest {

    @InjectMocks
    private ValidatorStartTime validatorStartTime;

    @Mock
    private PostSessionDTO postSessionDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenStartTimeIsInTheFuture_thenNoExceptionIsThrown() {
        LocalDateTime futureTime = LocalDateTime.now().plusHours(1);
        when(postSessionDto.startTime()).thenReturn(futureTime);

        validatorStartTime.validate(postSessionDto);
    }

    @Test
    public void whenStartTimeIsInThePast_thenExceptionIsThrown() {
        LocalDateTime pastTime = LocalDateTime.now().minusHours(1);
        when(postSessionDto.startTime()).thenReturn(pastTime);

        assertThrows(SessionException.class, () -> validatorStartTime.validate(postSessionDto));
    }
}

