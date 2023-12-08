package senior.com.br.CineTickets.domain.session.validation;

import org.springframework.stereotype.Component;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;

import java.time.LocalDateTime;

@Component
public class ValidatorCinemaIsOpen implements SessionValidator {

    public void validate(PostSessionDTO dto) {
        LocalDateTime sessionStartTime = dto.startTime();
        int openingHour = 10; // 10 am
        int closingHour = 22; // 10 pm

        int sessionHour = sessionStartTime.getHour();
        if (sessionHour < openingHour || sessionHour >= closingHour) {
            throw new SessionException("Sessions can only be scheduled between 10 am and 10 pm.");
        }
    }
}



