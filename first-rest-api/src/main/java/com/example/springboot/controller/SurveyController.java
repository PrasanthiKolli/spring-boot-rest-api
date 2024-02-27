package com.example.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.springboot.model.Question;
import com.example.springboot.model.Survey;
import com.example.springboot.service.SurveyService;

@RestController
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	@RequestMapping("/surveys")
	public List<Survey> getAllSurveys() {

		return surveyService.getAllSurveys();
	}

	@RequestMapping("/surveys/{surveyId}")
	public Survey getSurveyById(@PathVariable String surveyId) {
		Survey survey = surveyService.getSurveyById(surveyId);
		if (survey == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return survey;
	}

	@RequestMapping("/surveys/{surveyId}/questions")
	public List<Question> getAllQuestionFromSurvey(@PathVariable String surveyId) {

		List<Question> questions = surveyService.getAllQuestionFromSurvey(surveyId);
		if (questions == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return questions;
	}

	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question getAQuestionFromSurveyByQuestionId(@PathVariable String surveyId, @PathVariable String questionId) {

		Question question = surveyService.getAQuestionFromSurveyByQuestionId(surveyId, questionId);
		if (question == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return question;
	}

	@RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {

		String questionId = surveyService.addNewSurveyQuestion(surveyId, question);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId)
				.toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		surveyService.deleteSurveyQuestion(surveyId, questionId);
		return ResponseEntity.noContent().build();
		
	}
}
