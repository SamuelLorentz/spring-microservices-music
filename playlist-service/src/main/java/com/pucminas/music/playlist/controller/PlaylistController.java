package com.pucminas.music.playlist.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pucminas.music.playlist.exception.InsertTrackException;
import com.pucminas.music.playlist.exception.RatingOutOfBoundsException;
import com.pucminas.music.playlist.model.Playlist;
import com.pucminas.music.playlist.model.transfer.TrackBody;
import com.pucminas.music.playlist.service.PlaylistService;

/**
 * 
 * @author LorentSB
 *
 */
@RestController
@RequestMapping(value = "/v1/playlists")
public class PlaylistController {

	@Autowired
	private PlaylistService playlistService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Playlist> getPlaylistById(@PathVariable Integer id){
		Playlist playlist = playlistService.getPlaylistById(id);
		return ResponseEntity.ok().body(playlist);
	}

	@GetMapping
	public ResponseEntity<List<Playlist>> getAllPlaylists(){
		List<Playlist> playlists = playlistService.getAllPlaylists();
		return ResponseEntity.ok().body(playlists);
	}

	@PostMapping
	public ResponseEntity<Playlist> createPlaylist() {
		Playlist playlist = playlistService.createPlaylist();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(playlist.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}/rating/{rating}")
	public ResponseEntity<Playlist> ratePlaylist(@PathVariable Integer id, @PathVariable Integer rating) throws RatingOutOfBoundsException {
		Playlist playlist = playlistService.ratePlaylist(id, rating);
		return ResponseEntity.ok().body(playlist);
	}

	@PutMapping(value = "/{id}/track")
	@HystrixCommand(fallbackMethod = "fallbackTrack", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public ResponseEntity<Playlist> insertTrackInPlaylist(@PathVariable Integer id, @RequestBody TrackBody trackBody) throws InsertTrackException{
		Playlist playlist = playlistService.insertTrackInPlaylist(id, trackBody);
		return ResponseEntity.ok().body(playlist);
	}
	
	@PutMapping(value = "/{id}/tracks")
	@HystrixCommand(fallbackMethod = "fallbackTrack", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public ResponseEntity<Playlist> insertTrackInPlaylist(@PathVariable Integer id, @RequestBody List<TrackBody> trackBodies) throws InsertTrackException{
		Playlist playlist = playlistService.insertListOfTracksInPlaylist(id, trackBodies);
		return ResponseEntity.ok().body(playlist);
	}

	@DeleteMapping(value = "/{id}/track/{trackId}")
	public ResponseEntity<Playlist> deleteTrackInPlaylist(@PathVariable Integer id, @PathVariable String trackId) {
		Playlist playlist = playlistService.deleteTrackInPlaylist(id, trackId);
		return ResponseEntity.ok().body(playlist);
	}

	@DeleteMapping(value = "/{id}")
	public void deletePlaylist(@PathVariable("id") Integer playlistId) {
		playlistService.deletePlaylist(playlistId);
	}

	@SuppressWarnings("unused")
	private String fallbackTrack() {
		return "Request fails. It takes long time to response";
	}
	
}
