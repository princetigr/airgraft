package com.airgraft.autocomplete;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AutoCompleteControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenHttpRequestWhenInvalidRequestReturnHttp400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/suggestions").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenHttpRequestWhenMissingCoordinatesReturnHttp200() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/suggestions").
				param("q","London").
				accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void givenHttpRequestWhenMissingQueryReturnHttp400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/suggestions").
				param("latitude","34.00").
				param("longitude","32.786").
				accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenHttpRequestWhenUnknownPositionReturnHttpOKWithEmptyResult() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/suggestions")
				.param("q","London")
				.param("latitude","34.0")
				.param("longitude","22.0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void givenHttpRequestWhenInvalidCoordinatesReturnHttp400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/suggestions")
				.param("q","London")
				.param("latitude","x")
				.param("longitude","x")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}