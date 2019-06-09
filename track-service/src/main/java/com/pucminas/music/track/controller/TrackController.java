package com.pucminas.music.track.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pucminas.music.track.model.dto.TrackDTO;
import com.pucminas.music.track.service.TrackService;
import com.pucminas.music.track.utils.TrackList;

@RestController
@RequestMapping(value = "/v1/tracks")
public class TrackController {

	@Autowired
	private TrackService trackService;

	@GetMapping(path = "/track")
	public ResponseEntity<TrackDTO> trackById(@RequestParam(value = "token") String token,
										      @RequestParam(value = "trackId") String trackId) throws Exception {
		return ResponseEntity.ok().body(trackService.getTrackById(token, trackId));
	}

	@GetMapping
	public ResponseEntity<TrackList> getSeveralTracks(@RequestParam(value = "token") String token,
													  @RequestParam(value = "trackIds") List<String> trackIds,
													  @RequestParam(value = "market") String market) throws Exception {
		TrackList listTracks = trackService.getSeveralTracks(token, trackIds, market);
		return ResponseEntity.ok().body(listTracks);
	}
}
