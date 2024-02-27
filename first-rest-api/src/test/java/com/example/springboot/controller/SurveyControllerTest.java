package com.example.springboot.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
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
	@Autowired
	private TestRestTemplate template;

	@Test
	void getAllQuestionFromSurvey_basicScenario() {
		String expectedResponse="""
				{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				""";

		ResponseEntity<String> response = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);
		
		assertEquals(expectedResponse.trim(), response.getBody());
	}

}
