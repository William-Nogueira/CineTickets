    package senior.com.br.CineTickets.domain.ticket.validation;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    import senior.com.br.CineTickets.domain.session.SessionEntity;
    import senior.com.br.CineTickets.domain.session.SessionRepository;
    import senior.com.br.CineTickets.domain.ticket.DTO.PostTicketDTO;

    import java.time.LocalDateTime;

    @Component
    public class ValidatorSessionIsActive implements TicketValidator {

        @Autowired
        private SessionRepository sessionRepository;

        @Override
        public void validate(PostTicketDTO dto) {
            SessionEntity session = sessionRepository.findById(dto.session_id())
                    .orElseThrow(() -> new TicketException("Session not found"));

            LocalDateTime sessionEndTime = session.getStartTime().plusMinutes(session.getDuration());
            LocalDateTime currentTime = LocalDateTime.now();

            if (currentTime.isAfter(sessionEndTime)) {
                throw new TicketException("Cannot create ticket for a session that has already ended.");
            }
        }
    }