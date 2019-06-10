package com.pucminas.music.track.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pucminas.music.track.exceptions.TrackException;

@ControllerAdvice
public class TrackControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TrackException.class)
	public void springHandleNotFound(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}
	
}
