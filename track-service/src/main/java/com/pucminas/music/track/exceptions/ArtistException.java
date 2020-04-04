package com.pucminas.music.track.exceptions;

public class ArtistException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArtistException(Exception e) {
		super("Error at API Call, or Invalid Token.", e);
	}
}
