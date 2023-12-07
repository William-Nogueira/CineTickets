package senior.com.br.CineTickets.domain.session.validation;

import org.springframework.stereotype.Component;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;

import java.time.LocalDateTime;

@Component
public class ValidatorStartTime implements SessionValidator {

    public void validate(PostSessionDTO dto) {
        if (dto.startTime().isBefore(LocalDateTime.now())) {
            throw new SessionException("The session start time cannot be in the past.");
        }
    }
}

