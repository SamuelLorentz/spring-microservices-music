package com.pucminas.music.track.exceptions;

public class TrackException extends Exception {

	private static final long serialVersionUID = 1L;

	public TrackException(Exception e) {
		super("Error at API Call, or Invalid Token.", e);
	}
}
