package com.airgraft.autocomplete;

import com.airgraft.autocomplete.rest.SuggestionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutoCompleteControllerIT {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port);
    }

    @Test
    public void givenHttpRequestWhenPosition_ReturnHttpOK() throws Exception {
        ResponseEntity<SuggestionResponse> response = template.getForEntity(base.toString()  + "/suggestions?q=Lon&latitude=34.3443&longitude=45.3343",
                SuggestionResponse.class);
        assertThat(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void givenHttpRequestWhenUnknownPosition_ReturnHttpOKWithEmptyResult() throws Exception {
        ResponseEntity<SuggestionResponse> response = template.getForEntity(base.toString()  + "/suggestions?q=SomeRandomCityInTheMiddleOfNowhere",
                SuggestionResponse.class);
        assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSuggestions()).isEmpty();

    }
}
