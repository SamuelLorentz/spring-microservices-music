package com.pucminas.music.track.service;

import com.pucminas.music.track.exceptions.ArtistException;
import com.pucminas.music.track.model.Artist;
import com.pucminas.music.track.model.ArtistList;
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
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
public class ArtistService {

	private static String URL = "https://api.spotify.com/v1/artists";

	private static RestTemplate restTemplate;

	@Autowired
	public ArtistService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * https://api.spotify.com/v1/artists/2CIMQHirSU0MQqyYHq0eOx
	 */
	public Artist getArtistById(String token, String artist) throws ArtistException {

		try {

			return restTemplate.exchange(URL, GET, getHttpEntity(token), Artist.class).getBody();

		} catch (Exception e) {
			throw new ArtistException(e);
		}
	}

	public ArtistList getSeveralArtistsById(String token, List<String> artistIds) throws ArtistException {

		try {

			return restTemplate.exchange(URL, GET, getHttpEntity(token), ArtistList.class).getBody();

		} catch (Exception e) {
			throw new ArtistException(e);
		}
	}

	/**
	 * https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?country=ES
	 * https://api.spotify.com/v1/artists/2CIMQHirSU0MQqyYHq0eOx/top-tracks?country=ES
	 */
	public TrackList getArtistTopTracks(String token, String artistId, String country) throws ArtistException {

		try {

			return restTemplate.exchange(uriBuilder(artistId, country), GET, getHttpEntity(token), TrackList.class).getBody();

		} catch (Exception e) {
			throw new ArtistException(e);
		}
	}

	private String uriBuilder(String id, String country) {
		return fromHttpUrl(getUrl(id))
				.queryParam("country", country)
				.toUriString();
	}

	private String getUrl(String id) {
		return URL.concat("/")
				.concat(id)
				.concat("/")
				.concat("top-tracks");
	}

	private HttpEntity getHttpEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(singletonList(APPLICATION_JSON));
		headers.setContentType(APPLICATION_JSON);
		headers.setBearerAuth(token);

		return new HttpEntity<>("body", headers);
	}
}
