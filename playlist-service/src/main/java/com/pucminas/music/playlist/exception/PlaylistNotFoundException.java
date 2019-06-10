package com.pucminas.music.playlist.exception;

/**
 * 
 * @author LorentSB
 *
 */
public class PlaylistNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlaylistNotFoundException(Integer playlistId) {
        super("Playlist id not found : " + playlistId);
    }

}
