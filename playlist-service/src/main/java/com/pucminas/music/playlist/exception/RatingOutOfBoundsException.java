package com.pucminas.music.playlist.exception;

/**
 * 
 * @author LorentSB
 *
 */
public class RatingOutOfBoundsException extends Exception {

	private static final long serialVersionUID = 1L;

	public RatingOutOfBoundsException(Integer rating) {
        super("Rating must be between 0 and 10 : " + rating);
    }
}