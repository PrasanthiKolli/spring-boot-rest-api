package com.example.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.springboot.model.Survey;
import com.example.springboot.service.SurveyService;

@RestController
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;


	@RequestMapping("/surveys")
	public List<Survey> getAllSurveys(){
		
		return surveyService.getAllSurveys();
	}
	
	@RequestMapping("/surveys/{id}")
	public Survey getSurveyById(@PathVariable String id){
		Survey survey=surveyService.getSurveyById(id);
		if(survey==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return surveyService.getSurveyById(id);
	}
}
