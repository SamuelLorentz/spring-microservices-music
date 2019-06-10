package com.pucminas.music.track.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucminas.music.track.connections.RequestArtistfromSpotify;
import com.pucminas.music.track.exceptions.ArtistException;
import com.pucminas.music.track.model.Artist;
import com.pucminas.music.track.utils.ArtistList;
import com.pucminas.music.track.utils.TrackList;

@Service
public class ArtistService {

	/**
	 * Get a Artist by Id
	 * 
	 * @param token
	 * @param artistId
	 * @return
	 * @throws Exception
	 */
	public Artist getArtistById(String token, String artistId) throws ArtistException {
		return getRequest().getArtistbyId(token, artistId);
	}

	/**
	 * Get multiple Artists
	 * 
	 * @param token
	 * @param artistIds
	 * @return
	 * @throws Exception
	 */
	public ArtistList getSeveralArtists(String token, List<String> artistIds) throws ArtistException {
		return getRequest().getSeveralArtistsById(token, artistIds);
	}
	
	/**
	 * Get an Artist's top tracks
	 * 
	 * @param token
	 * @param artistId
	 * @return
	 * @throws Exception
	 */
	public TrackList getArtistTopTracks(String token, String artistId, String country) throws ArtistException {
		return getRequest().getArtistTopTracks(token, artistId, country);
	}

	private RequestArtistfromSpotify getRequest() {
		return new RequestArtistfromSpotify();
	}
}
