package com.pucminas.music.track.controller;

import com.pucminas.music.track.exceptions.ArtistException;
import com.pucminas.music.track.model.Artist;
import com.pucminas.music.track.service.ArtistService;
import com.pucminas.music.track.model.ArtistList;
import com.pucminas.music.track.model.TrackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/artists")
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<Artist> artistById(@RequestHeader(value = "token") String token,
										     @RequestAttribute(value = "id") String artistId) throws ArtistException {
		return ResponseEntity.ok().body(artistService.getArtistById(token, artistId));
	}

	@GetMapping
	public ResponseEntity<ArtistList> getSeveralArtists(@RequestHeader(value = "token") String token,
												 	    @RequestParam(value = "artistIds") List<String> artistIds) throws ArtistException {
		return ResponseEntity.ok().body(artistService.getSeveralArtistsById(token, artistIds));
	}
	
	@GetMapping(path = "{id}/top-tracks")
	public ResponseEntity<TrackList> getTopTracksByArtistAndCountry(
																	@RequestHeader(value = "token") String token,
																	@RequestAttribute(value = "id") String artistId,
																	@RequestParam(value = "country") String country) throws ArtistException {
		TrackList trackList = artistService.getArtistTopTracks(token, artistId, country);
		return ResponseEntity.ok().body(trackList);
	}
}