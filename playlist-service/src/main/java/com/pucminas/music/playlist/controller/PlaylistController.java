package com.pucminas.music.playlist.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pucminas.music.playlist.exception.InsertTrackException;
import com.pucminas.music.playlist.exception.RatingOutOfBoundsException;
import com.pucminas.music.playlist.model.Playlist;
import com.pucminas.music.playlist.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/v1/playlists")
public class PlaylistController {

	@Autowired
	private PlaylistService playlistService;

	@GetMapping
	public ResponseEntity<List<Playlist>> getAllPlaylists(){
		return ResponseEntity.ok().body(playlistService.getAllPlaylists());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Playlist> getPlaylistById(@PathVariable Integer id){
		return ResponseEntity.ok().body(playlistService.getPlaylistById(id));
	}

	@PostMapping
	public ResponseEntity<Playlist> createPlaylist(Playlist playlist) {
		playlist = playlistService.createPlaylist(playlist);
		URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(playlist.getId()).toUri();
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
