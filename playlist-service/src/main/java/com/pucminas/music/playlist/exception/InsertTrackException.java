package com.pucminas.music.playlist.exception;

public class InsertTrackException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsertTrackException() {
		super("Invalid Token, or track alredy exists in Playlist");
	}
}
