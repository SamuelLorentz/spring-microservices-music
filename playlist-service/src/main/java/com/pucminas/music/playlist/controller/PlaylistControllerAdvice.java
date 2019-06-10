package com.pucminas.music.playlist.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pucminas.music.playlist.exception.InsertTrackException;
import com.pucminas.music.playlist.exception.PlaylistNotFoundException;
import com.pucminas.music.playlist.exception.RatingOutOfBoundsException;

@ControllerAdvice
public class PlaylistControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PlaylistNotFoundException.class)
	public void springHandleNotFound(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}
	
	@ExceptionHandler(InsertTrackException.class)
	public void springHandleServerError(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@ExceptionHandler(RatingOutOfBoundsException.class)
	public void springHandleInvalidRate(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

}
