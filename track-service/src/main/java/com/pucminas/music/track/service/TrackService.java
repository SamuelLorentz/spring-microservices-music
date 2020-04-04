package com.pucminas.music.track.service;

import com.pucminas.music.track.exceptions.TrackException;
import com.pucminas.music.track.model.Track;
import com.pucminas.music.track.model.TrackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class TrackService {

	private static String URL = "https://api.spotify.com/v1/tracks";

	private static RestTemplate restTemplate;

	@Autowired
	public TrackService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Track getTrackById(String token, String trackId) throws TrackException {

		try {

			return restTemplate.exchange(URL, GET, getHttpEntity(token), Track.class).getBody();

		} catch (Exception e) {
			throw new TrackException(e);
		}
	}

	public TrackList getSeveralTracksById(String token, List<String> trackIds, String market) throws TrackException {

		try {

			return restTemplate.exchange(URL, GET, getHttpEntity(token), TrackList.class).getBody();

		} catch (Exception e) {
			throw new TrackException(e);
		}
	}

	private HttpEntity getHttpEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(singletonList(APPLICATION_JSON));
		headers.setContentType(APPLICATION_JSON);
		headers.setBearerAuth(token);

		return new HttpEntity<>("body", headers);
	}
}