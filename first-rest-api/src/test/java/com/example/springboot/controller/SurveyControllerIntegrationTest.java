package com.example.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyControllerIntegrationTest {

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
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);


		ResponseEntity<String> response = template.exchange(SPECIFIC_QUESTION_URL,HttpMethod.GET,httpEntity, String.class);

		assertEquals(expectedResponse.trim(), response.getBody());

		assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
		assertTrue(response.getStatusCode().is2xxSuccessful());

		JSONAssert.assertEquals(str, response.getBody(), true);
		JSONAssert.assertEquals(expectedResponse_IgoringFewFields, response.getBody(), false);
	}

	@Test
	void getAllQuestionFromSurveyTest() throws JSONException {
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
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = template.exchange(GENRIC_QUESTION_URL,HttpMethod.GET,httpEntity, String.class);

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
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = template.exchange(GENRIC_SURVEY_URL,HttpMethod.GET,httpEntity, String.class);

		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}

	@Test
	void getSurveyByIdTest() throws JSONException {
		String expectedResponse = """
						    {
						        "id": "Survey1"
						    }
				""";
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = template.exchange(SPECIFIC_SURVEY_URL,HttpMethod.GET,httpEntity, String.class);


		JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
	}

	@Test
	void addNewSurveyQuestion_basicScenario() throws JSONException {
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
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(body, headers);
		ResponseEntity<String> response = template.exchange(GENRIC_QUESTION_URL, HttpMethod.POST, httpEntity,
				String.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		String locationHeader = response.getHeaders().get("location").get(0);
		assertTrue(locationHeader.contains(GENRIC_QUESTION_URL));
		
		ResponseEntity<String> responseEntityDelete 
		= template.exchange(locationHeader, HttpMethod.DELETE, httpEntity, String.class);

		assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());
		//template.delete(locationHeader);
	}
	private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin","password"));
		return headers;
	}
	
	String performBasicAuthEncoding(String user, String password) {
		String combined = user + ":" + password;
		byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
		return new String(encodedBytes);
	}

}
