package com.pucminas.music.playlist.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pucminas.music.playlist.exception.InsertTrackException;
import com.pucminas.music.playlist.exception.PlaylistNotFoundException;
import com.pucminas.music.playlist.exception.RatingOutOfBoundsException;
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

	private final DiscoveryClient discoveryClient;

	@Value("TRACK-SERVICE")
	private String trackServiceId;

	@Value("/v1/tracks/track")
	private String tracksEndpointUri;

	@Autowired
	public PlaylistService(PlaylistRepository playlistRepository, TrackRepository trackRepository,
			RestTemplate restTemplate, DiscoveryClient discoveryClient) {
		this.playlistRepository = playlistRepository;
		this.trackRepository = trackRepository;
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	/**
	 * Create a new empty playlist.
	 * 
	 * @return
	 */
	public Playlist createPlaylist() {
		Playlist playlist = new Playlist();
		playlist.setRating(0);
		return playlistRepository.save(playlist);
	}

	/**
	 * Insert single track in playlist if it doesn't alredy have it.
	 * 
	 * @param playlistId
	 * @param trackBody
	 * @return
	 * @throws InsertTrackException 
	 */
	public Playlist insertTrackInPlaylist(Integer playlistId, TrackBody trackBody) throws InsertTrackException {
		Playlist playlist = getPlaylistById(playlistId);

		if (playlist.getTracks() == null) {
			playlist.setTracks(new ArrayList<>());
		}

		playlist = saveTrackInPlaylist(playlist, trackBody);

		return playlist;
	}

	/**
	 * Insert List of tracks in playlist.
	 * 
	 * @param id
	 * @param trackBodies
	 * @return
	 * @throws InsertTrackException 
	 */
	public Playlist insertListOfTracksInPlaylist(Integer id, List<TrackBody> trackBodies) throws InsertTrackException {
		Playlist playlist = getPlaylistById(id);

		if (playlist.getTracks() == null) {
			playlist.setTracks(new ArrayList<>());
		}

		for (TrackBody trackBody : trackBodies) {

			playlist = saveTrackInPlaylist(playlist, trackBody);
		}

		return playlist;
	}

	/**
	 * Delete track in playlist by Spotify's track id.
	 * 
	 * @param playlistId
	 * @param trackId
	 * @return
	 */
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

	/**
	 * Get All playlists.
	 * 
	 * @return
	 */
	public List<Playlist> getAllPlaylists() {
		return (List<Playlist>) playlistRepository.findAll();
	}

	/**
	 * Get playlist by Id.
	 * 
	 * @param playlistId
	 * @return
	 */
	public Playlist getPlaylistById(Integer playlistId) {
		Optional<Playlist> obj = playlistRepository.findById(playlistId);
		return obj.orElseThrow(() -> new PlaylistNotFoundException(playlistId));
	}

	/**
	 * Rate playlist between 0 and 10 stars.
	 * 
	 * @param playlistId
	 * @param rating
	 * @return
	 * @throws Exception
	 */
	public Playlist ratePlaylist(Integer playlistId, Integer rating) throws RatingOutOfBoundsException {
		Playlist playlist = getPlaylistById(playlistId);
		if (rating > 0 && rating < 10) {
			playlist.setRating(rating);
			return playlistRepository.save(playlist);
		} else {
			throw new RatingOutOfBoundsException(rating);
		}
	}

	/**
	 * Delete entire playlist.
	 * 
	 * @param playlistId
	 */
	public void deletePlaylist(Integer playlistId) {
		playlistRepository.deleteById(playlistId);
	}

	/**
	 * Include track in playlist.
	 * 
	 * @param playlist
	 * @param trackBody
	 * @return
	 * @throws InsertTrackException 
	 */
	private Playlist saveTrackInPlaylist(Playlist playlist, TrackBody trackBody) throws InsertTrackException {
		try {
			TrackDTO trackDTO = restTemplate.getForObject(getRequest(trackBody), TrackDTO.class);
			Track fromRepo = saveTrack(trackDTO);

			if (!playlist.getTracks().contains(fromRepo)) {

				playlist.getTracks().add(fromRepo);
				playlist = playlistRepository.save(playlist);
			}
			
			return playlist;
		
		} catch (Exception e) {
			throw new InsertTrackException();
		}
	}

	/**
	 * Save track in DB.
	 * 
	 * @param trackDTO
	 * @return
	 */
	private Track saveTrack(TrackDTO trackDTO) {
		Track track = new Track(trackDTO.getTrack(), trackDTO.getName(), trackDTO.getType());

		Track fromRepo = trackRepository.findByTrackId(track.getTrackId());

		if (fromRepo == null) {
			fromRepo = trackRepository.save(track);
		}
		return fromRepo;
	}

	/**
	 * URI from Spotify's API.
	 * 
	 * @param trackId
	 * @return
	 */
	private String getRequest(TrackBody trackId) {
		URI uri = getInstance(trackServiceId);
		return uri.toString() + tracksEndpointUri + "?token=" + trackId.getToken() + "&trackId=" + trackId.getTrackId()
				+ "&market=" + trackId.getMarket();
	}
	
	/**
	 * Discover TrackService instance
	 * 
	 * @param serviceId
	 * @return
	 */
	public URI getInstance(String serviceId) {

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

		if (instances != null && !instances.isEmpty()) {
			return instances.get(0).getUri();
		}

		return null;

	}

}
