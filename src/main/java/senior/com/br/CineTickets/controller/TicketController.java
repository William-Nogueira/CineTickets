package senior.com.br.CineTickets.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import senior.com.br.CineTickets.domain.ticket.DTO.GetTicketDTO;
import senior.com.br.CineTickets.domain.ticket.DTO.PostTicketDTO;
import senior.com.br.CineTickets.domain.ticket.TicketService;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<GetTicketDTO> newTicket(@RequestBody @Valid PostTicketDTO dto, UriComponentsBuilder uriBuilder) {
        var data = ticketService.newTicket(dto);
        return ResponseEntity.ok(data);
    }

    @GetMapping
    public ResponseEntity<Page<GetTicketDTO>> listAllTickets(@PageableDefault(size = 10, sort = {"id"}) Pageable paging) {
        var page = ticketService.listAllTickets(paging);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTicketDTO> getTicketById(@PathVariable Long id) {
        var ticketDto = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticketDto);
    }

}

