package com.pucminas.music.track.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucminas.music.track.connections.RequestTrackfromSpotify;
import com.pucminas.music.track.model.Track;
import com.pucminas.music.track.model.dto.TrackDTO;
import com.pucminas.music.track.utils.TrackList;

@Service
public class TrackService {

	/**
	 * Get a Track by Id
	 * 
	 * @param token
	 * @param trackId
	 * @return
	 * @throws Exception
	 */
	public TrackDTO getTrackById(String token, String trackId) throws Exception {
		Track track = getRequest().getTrackbyId(token, trackId);
		return new TrackDTO(track.getId(), track.getName(), track.getType());
	}

	/**
	 * Get multiple Tracks
	 * 
	 * @param token
	 * @param trackIds
	 * @param market
	 * @return
	 * @throws Exception
	 */
	public TrackList getSeveralTracks(String token, List<String> trackIds, String market) throws Exception {
		return getRequest().getSeveralTracksById(token, trackIds, market);
	}

	private RequestTrackfromSpotify getRequest() {
		return new RequestTrackfromSpotify();
	}
}
