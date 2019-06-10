package com.pucminas.music.track.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.pucminas.music.track.exceptions.ArtistException;
import com.pucminas.music.track.model.Artist;
import com.pucminas.music.track.utils.ArtistList;
import com.pucminas.music.track.utils.TrackList;

public class RequestArtistfromSpotify {

	private static String artistURI = "https://api.spotify.com/v1/artists";
	private static String getERROR = "GET request not worked";

	static Logger log = Logger.getLogger(RequestArtistfromSpotify.class.getName());

	/**
	 * Return a Artist by his Id
	 * 
	 * https://api.spotify.com/v1/artists/2CIMQHirSU0MQqyYHq0eOx
	 * 
	 * @param token
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Artist getArtistbyId(String token, String query) throws ArtistException {

		try {
			HttpURLConnection con = getConnection(token, "/" + query);

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return new Gson().fromJson(readResponse(con).toString(), Artist.class);
			} else {
				log.info(getERROR);
				return null;
			}
		} catch (Exception e) {
			throw new ArtistException();
		}
	}

	/**
	 * Return several Artists by their Ids
	 * 
	 * @param token
	 * @param ArtistIds
	 * @param market
	 * @return
	 * @throws Exception
	 */
	public ArtistList getSeveralArtistsById(String token, List<String> artistIds) throws ArtistException {

		try {
			HttpURLConnection con = getConnection(token, getArtistParams(artistIds).toString());

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return new Gson().fromJson(readResponse(con).toString(), ArtistList.class);
			} else {
				log.info(getERROR);
				return null;
			}
		} catch (Exception e) {
			throw new ArtistException();
		}
	}

	/**
	 * Return several Artists by their Ids
	 * 
	 * https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?country=ES
	 * https://api.spotify.com/v1/artists2CIMQHirSU0MQqyYHq0eOx/top-tracks?country=ES
	 * 
	 * @param token
	 * @param ArtistIds
	 * @return
	 * @throws Exception
	 */
	public TrackList getArtistTopTracks(String token, String artistId, String country) throws ArtistException {

		try {
			HttpURLConnection con = getConnection(token, getArtistTopTracks("/" + artistId, country).toString());

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return new Gson().fromJson(readResponse(con).toString(), TrackList.class);
			} else {
				log.info(getERROR);
				return null;
			}
		} catch (Exception e) {
			throw new ArtistException();
		}
	}

	/**
	 * Returns a StringBuilder with the JSON as a String from the URI
	 * 
	 * @param con
	 * @return
	 * @throws IOException
	 */
	private StringBuilder readResponse(HttpURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}

	/**
	 * Build the request params for multiple Artists
	 * 
	 * @param ArtistIds
	 * @param market
	 * @return
	 */
	private StringBuilder getArtistParams(List<String> artistIds) {
		StringBuilder artistParams = new StringBuilder();
		artistParams.append("?ids=");
		int count = 0;
		for (String id : artistIds) {
			if (count == 0) {
				artistParams.append(id);
				count++;
			} else {
				artistParams.append("%" + id);
			}
		}

		return artistParams;
	}

	/**
	 * Build the request params for multiple Artists
	 * https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?country=ES
	 * 
	 * @param ArtistIds
	 * @param market
	 * @return
	 */
	private StringBuilder getArtistTopTracks(String artistId, String country) {
		StringBuilder params = new StringBuilder();
		params.append(artistId + "/top-tracks?country=" + country);
		return params;
	}

	/**
	 * Create a curl Get request for the Spotify Artist endpoints: Artists/{id} and
	 * Artists (with id parameters)
	 * 
	 * https://api.spotify.com/v1/artists?ids=2CIMQHirSU0MQqyYHq0eOx%2C57dN52uHvrHOxijzpIgu3E%2C1vCWHaC5f2uS3yhpwWbIA6
	 * 
	 * @param token
	 * @param query
	 * @return
	 */
	private HttpURLConnection getConnection(String token, String query) {
		try {
			URL obj = new URL(artistURI + query);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "Bearer " + token);
			con.setRequestProperty("Accept", "application/json");
			log.info("GET Response Code :: " + con.getResponseCode());
			return con;
		} catch (IOException e) {
			log.info("*** ARTIST REQUEST ERROR ***");
			e.printStackTrace();
			return null;
		}
	}

}
