package com.example.springboot.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyControllerTest {

	String str = """
			{
			    "id": "Question1",
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
	// http://localhost:8085/surveys/Survey1/questions/Question1

	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
	private static String GENRIC_QUESTION_URL = "/surveys/Survey1/questions";
	private static String GENRIC_SURVEY_URL = "/surveys";
	private static String SPECIFIC_SURVEY_URL = "/surveys/Survey1";
	@Autowired
	private TestRestTemplate template;

	@Test
	void geAQuestionFromSurvey_basicScenario() throws JSONException {
		String expectedResponse = """
				{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				""";
		String expectedResponse_IgoringFewFields = """
				{
				    "id": "Question1",
				    "description": "Most Popular Cloud Platform Today",
				    "correctAnswer": "AWS"
				}
				""";

		ResponseEntity<String> response = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		assertEquals(expectedResponse.trim(), response.getBody());

		assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
		assertTrue(response.getStatusCode().is2xxSuccessful());

		JSONAssert.assertEquals(str, response.getBody(), true);
		JSONAssert.assertEquals(expectedResponse_IgoringFewFields, response.getBody(), false);
	}

	@Test
	void getAQuestionFromSurveyByQuestionIdTest() throws JSONException {
		String expectedResponse = """
						[
						    {
						        "id": "Question1"
						    },
						    {
						        "id": "Question2"
						    },
						    {
						        "id": "Question3"
						    }
						]
				""";

		ResponseEntity<String> response = template.getForEntity(GENRIC_QUESTION_URL, String.class);

		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}
	
	@Test
	void getAllSurveysTest() throws JSONException {
		String expectedResponse = """
						[
						    {
						        "id": "Survey1"
						    }
						]
				""";

		ResponseEntity<String> response = template.getForEntity(GENRIC_SURVEY_URL, String.class);

		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}
	
	@Test
	void getSurveyByIdTest() throws JSONException {
		String expectedResponse = """
						    {
						        "id": "Survey1"
						    }
				""";

		ResponseEntity<String> response = template.getForEntity(SPECIFIC_SURVEY_URL, String.class);

		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}


}
