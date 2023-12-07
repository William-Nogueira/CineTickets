package senior.com.br.CineTickets.domain.session.validation;

import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;

public interface SessionValidator {
    void validate(PostSessionDTO dto);
}
