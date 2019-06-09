package com.pucminas.music.track.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.pucminas.music.track.model.Artist;
import com.pucminas.music.track.utils.ArtistList;
import com.pucminas.music.track.utils.TrackList;

public class RequestArtistfromSpotify {

	private static String artistURI = "https://api.spotify.com/v1/artists";

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
	public Artist getArtistbyId(String token, String query) throws Exception {

		HttpURLConnection con = getConnection(token, "/" + query);

		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return new Gson().fromJson(readResponse(con).toString(), Artist.class);
		} else {
			System.out.println("GET request not worked");
			return null;
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
	public ArtistList getSeveralArtistsById(String token, List<String> ArtistIds) throws Exception {

		HttpURLConnection con = getConnection(token, getArtistParams(ArtistIds).toString());

		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			ArtistList ArtistList = new Gson().fromJson(readResponse(con).toString(), ArtistList.class);
			return ArtistList;

		} else {
			System.out.println("GET request not worked");
			return null;
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
	public TrackList getArtistTopTracks(String token, String artistId, String country) throws Exception {

		HttpURLConnection con = getConnection(token, getArtistTopTracks("/" + artistId, country).toString());

		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			TrackList trackList = new Gson().fromJson(readResponse(con).toString(), TrackList.class);
			return trackList;

		} else {
			System.out.println("GET request not worked");
			return null;
		}

	}
	
	/**
	 * Returns a StringBuffer with the JSON as a String from the URI 
	 * 
	 * @param con
	 * @return
	 * @throws IOException
	 */
	private StringBuffer readResponse(HttpURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

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
	private StringBuilder getArtistParams(List<String> ArtistIds) {
		StringBuilder ArtistParams = new StringBuilder();
		ArtistParams.append("?ids=");
		int count = 0;
		for (String id : ArtistIds) {
			if (count == 0) {
				ArtistParams.append(id);
				count++;
			} else {
				ArtistParams.append("%" + id);
			}
		}

		return ArtistParams;
	}

	/**
	 * Build the request params for multiple Artists
	 * https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?country=ES
	 * 
	 * @param ArtistIds
	 * @param market
	 * @return
	 */
	private StringBuilder getArtistTopTracks(String ArtistId, String country) {
		StringBuilder params = new StringBuilder();
		params.append(ArtistId + "/top-tracks?country=" + country);
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
			System.out.println("GET Response Code :: " + con.getResponseCode());
			return con;
		} catch (IOException e) {
			System.out.println("*** ARTIST REQUEST ERROR ***");
			e.printStackTrace();
			return null;
		}
	}

}
