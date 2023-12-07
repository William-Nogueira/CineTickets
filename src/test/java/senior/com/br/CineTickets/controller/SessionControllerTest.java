package senior.com.br.CineTickets.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import senior.com.br.CineTickets.domain.movie.Movie;
import senior.com.br.CineTickets.domain.session.DTO.GetSessionDTO;
import senior.com.br.CineTickets.domain.session.DTO.PostSessionDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<PostSessionDTO> postJson;

    @Autowired
    private JacksonTester<GetSessionDTO> getJson;

    @Test
    @DisplayName("Deveria devolver codigo http 500 quando faltar informações")
    void attemptToMakeASession1() throws Exception {
        var response = mvc.perform(post("/session"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    void attemptToMakeASession2() throws Exception {

    }

}
