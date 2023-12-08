package senior.com.br.CineTickets.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;
import senior.com.br.CineTickets.domain.session.SessionEntity;
import senior.com.br.CineTickets.domain.session.SessionRepository;
import senior.com.br.CineTickets.domain.session.validation.SessionException;
import senior.com.br.CineTickets.domain.session.validation.ValidatorRoomIsFree;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class ValidatorRoomIsFreeTest {

    @InjectMocks
    private ValidatorRoomIsFree validatorRoomIsFree;

    @Mock
    private SessionRepository sessionRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenRoomIsFree_thenNoExceptionIsThrown() {
        PostSessionDTO postSessionDto = new PostSessionDTO(1L, 1L, LocalDateTime.now(), 100);
        LocalDateTime startTime = postSessionDto.startTime();
        LocalDateTime endTime = startTime.plusMinutes(postSessionDto.duration());
        when(sessionRepository.findOverlappingSessions(postSessionDto.room_id(), startTime, endTime))
                .thenReturn(Collections.emptyList());

        validatorRoomIsFree.validate(postSessionDto);
    }

    @Test
    public void whenRoomIsNotFree_thenExceptionIsThrown() {
        PostSessionDTO postSessionDto = new PostSessionDTO(1L, 1L, LocalDateTime.now(), 100);
        LocalDateTime startTime = postSessionDto.startTime();
        LocalDateTime endTime = startTime.plusMinutes(postSessionDto.duration());
        List<SessionEntity> overlappingSessions = Collections.singletonList(new SessionEntity());
        when(sessionRepository.findOverlappingSessions(postSessionDto.room_id(), startTime, endTime))
                .thenReturn(overlappingSessions);

        assertThrows(SessionException.class, () -> validatorRoomIsFree.validate(postSessionDto));
    }
}
