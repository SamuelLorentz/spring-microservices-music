package com.pucminas.music.track.controller;

import com.pucminas.music.track.exceptions.TrackException;
import com.pucminas.music.track.model.Track;
import com.pucminas.music.track.service.TrackService;
import com.pucminas.music.track.model.TrackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/tracks")
public class TrackController {

	@Autowired
	private TrackService trackService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<Track> getTrack(@RequestHeader(value = "token") String token,
										  @RequestAttribute(value = "id") String trackId) throws TrackException {
		return ResponseEntity.ok().body(trackService.getTrackById(token, trackId));
	}

	@GetMapping
	public ResponseEntity<TrackList> getSeveralTracks(@RequestHeader(value = "token") String token,
													  @RequestAttribute(value = "trackIds") List<String> trackIds,
													  @RequestParam(value = "market") String market) throws TrackException {
		return ResponseEntity.ok().body(trackService.getSeveralTracksById(token, trackIds, market));
	}
}