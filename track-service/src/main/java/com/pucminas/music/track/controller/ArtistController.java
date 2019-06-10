package com.pucminas.music.track.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pucminas.music.track.exceptions.ArtistException;
import com.pucminas.music.track.model.Artist;
import com.pucminas.music.track.service.ArtistService;
import com.pucminas.music.track.utils.ArtistList;
import com.pucminas.music.track.utils.TrackList;

@RestController
@RequestMapping(value = "/v1/artists")
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	@GetMapping(path = "/artist")
	public ResponseEntity<Artist> artistById(@RequestParam(value = "token") String token,
										     @RequestParam(value = "artistId") String artistId) throws ArtistException {
		return ResponseEntity.ok().body(artistService.getArtistById(token, artistId));
	}

	@GetMapping
	public ResponseEntity<ArtistList> getSeveralArtists(@RequestParam(value = "token") String token,
												 	    @RequestParam(value = "artistIds") List<String> artistIds) throws ArtistException {
		ArtistList listArtists = artistService.getSeveralArtists(token, artistIds);
		return ResponseEntity.ok().body(listArtists);
	}
	
	@GetMapping(path = "/top-tracks")
	public ResponseEntity<TrackList> getSeveralArtists(@RequestParam(value = "token") String token,
												 	   @RequestParam(value = "artistId") String artistId,
												       @RequestParam(value = "country") String country) throws ArtistException {
		TrackList trackList = artistService.getArtistTopTracks(token, artistId, country);
		return ResponseEntity.ok().body(trackList);
	}
	
	
}
