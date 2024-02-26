package com.example.springboot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.model.HelloWorld;

import jakarta.websocket.server.PathParam;

@RestController
public class HelloWorldResourse {

	@RequestMapping("hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	@RequestMapping("hello-world-bean")
	public HelloWorld helloWorldBean() {
		return new HelloWorld("Hello world");
	}

	@RequestMapping("hello-world/{name}")
	public HelloWorld helloWorldPathparam(@PathVariable String name) {
		return new HelloWorld("Hello " + name);
	}

	@RequestMapping("hello-world/{name}/message/{message}")
	public HelloWorld helloWorldMultiPathparam(@PathVariable String name, @PathVariable String message) {
		return new HelloWorld("Hello " + name + "," + message);
	}

}
