package com.pucminas.music.playlist.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pucminas.music.playlist.model.Playlist;
import com.pucminas.music.playlist.model.Track;
import com.pucminas.music.playlist.model.transfer.TrackDTO;
import com.pucminas.music.playlist.model.transfer.TrackBody;
import com.pucminas.music.playlist.repository.PlaylistRepository;
import com.pucminas.music.playlist.repository.TrackRepository;

@Service
public class PlaylistService {

	private final PlaylistRepository playlistRepository;

	private final TrackRepository trackRepository;

	private final RestTemplate restTemplate;

	private final EurekaService eurekaService;

	@Value("TRACK-SERVICE")
	private String trackServiceId;

	@Value("/v1/tracks/track")
	private String tracksEndpointUri;

	@Autowired
	public PlaylistService(PlaylistRepository playlistRepository, TrackRepository trackRepository,
			RestTemplate restTemplate, EurekaService eurekaService) {
		this.playlistRepository = playlistRepository;
		this.trackRepository = trackRepository;
		this.restTemplate = restTemplate;
		this.eurekaService = eurekaService;
	}

	public Playlist createPlaylist() {
		Playlist playlist = new Playlist();
		playlist.setRating(0);
		return playlistRepository.save(playlist);
	}

	public Playlist insertTrackInPlaylist(Integer playlistId, TrackBody trackBody) {
		Playlist playlist = getPlaylistById(playlistId);
		
		if (playlist.getTracks() == null) {
			playlist.setTracks(new ArrayList<>());
		}

		TrackDTO trackDTO = restTemplate.getForObject(getRequest(trackBody), TrackDTO.class);
		Track track = new Track(trackDTO.getTrack(), trackDTO.getName(), trackDTO.getType());

		Track fromRepo = trackRepository.findByTrackId(track.getTrackId());

		if (fromRepo == null) {
			fromRepo = trackRepository.save(track);
		} 
		
		if (!playlist.getTracks().contains(fromRepo)) {
			
			playlist.getTracks().add(fromRepo);
			playlist = playlistRepository.save(playlist);
		}
		
		return playlist;
	}

	public Playlist deleteTrackInPlaylist(Integer playlistId, String trackId) {
		Playlist playlist = getPlaylistById(playlistId);

		for (Track track : playlist.getTracks()) {
			if (track.getTrackId().equalsIgnoreCase(trackId)) {
				playlist.getTracks().remove(track);
				break;
			}
		}

		return playlistRepository.save(playlist);
	}

	public List<Playlist> getAllPlaylists() {
		return (List<Playlist>) playlistRepository.findAll();
	}

	public Playlist getPlaylistById(Integer playlistId) {
		Optional<Playlist> obj = playlistRepository.findById(playlistId);
		return obj.orElse(null);
	}

	public Playlist ratePlaylist(Integer playlistId, Integer rating) throws Exception {
		Playlist playlist = getPlaylistById(playlistId);
		if (rating > 0 && rating < 10) {
			playlist.setRating(rating);
			return playlistRepository.save(playlist);
		} else {
			throw new Exception();
		}
	}

	public void deletePlaylist(Integer playlistId) {
		playlistRepository.deleteById(playlistId);
	}

	private String getRequest(TrackBody trackId) {
		URI uri = eurekaService.getInstance(trackServiceId);
		String request = uri.toString() + tracksEndpointUri + "?token=" + trackId.getToken() + "&trackId="
				+ trackId.getTrackId() + "&market=" + trackId.getMarket();
		return request;
	}

}
