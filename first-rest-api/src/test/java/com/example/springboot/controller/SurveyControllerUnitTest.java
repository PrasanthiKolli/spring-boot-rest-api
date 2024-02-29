package com.example.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.springboot.model.Question;
import com.example.springboot.service.SurveyService;

@WebMvcTest(controllers = SurveyController.class)
@AutoConfigureMockMvc(addFilters = false)
class SurveyControllerUnitTest {

	@MockBean
	private SurveyService surveyService;

	@Autowired
	private MockMvc mockMvc;

	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
	private static String GENRIC_QUESTION_URL = "/surveys/Survey1/questions";

	@Test
	void geAQuestionFromSurvey_404Scenario() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	@Test
	void geAQuestionFromSurvey_basicScenario() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);

		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

		when(surveyService.getAQuestionFromSurveyByQuestionId("Survey1", "Question1")).thenReturn(question);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		String expectedResponse = """
				{

					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
					"correctAnswer":"AWS"

				}

				""";
		MockHttpServletResponse response = mvcResult.getResponse();

		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);

	}

	@Test
	void addNewSurveyQuestion_basicScenario() throws Exception {
		String body = """
					{
				    "description": "Most Popular Cloud Platform Today",
				    "options": [
				        "AWS",
				        "Azure",
				        "Google Cloud",
				        "Oracle Cloud"
				    ],
				    "correctAnswer": "AWS"
				}
					""";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GENRIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON).content(body).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		String locationHeader = response.getHeader("location");

		assertEquals(201, response.getStatus());
		assertTrue(locationHeader.contains(GENRIC_QUESTION_URL));
	}

}
