package com.pucminas.music.track.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.pucminas.music.track.model.Track;
import com.pucminas.music.track.utils.TrackList;

public class RequestTrackfromSpotify {

	private static String trackURI = "https://api.spotify.com/v1/tracks";

	/**
	 * Return a track by it's Id
	 * 
	 * @param token
	 * @param trackId
	 * @return
	 * @throws Exception
	 */
	public Track getTrackbyId(String token, String trackId) throws Exception {

		HttpURLConnection con = getConnection(token, "/" + trackId);

		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return new Gson().fromJson(response.toString(), Track.class);

		} else {
			System.out.println("GET request not worked");
		}

		return null;
	}

	/**
	 * Return several tracks by their Ids
	 * 
	 * @param token
	 * @param trackIds
	 * @param market
	 * @return
	 * @throws Exception
	 */
	public TrackList getSeveralTracksById(String token, List<String> trackIds, String market) throws Exception {

		HttpURLConnection con = getConnection(token, getTrackParams(trackIds, market).toString());

		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

			TrackList trackList = new Gson().fromJson(response.toString(), TrackList.class);
			return trackList;

		} else {
			System.out.println("GET request not worked");
		}

		return null;
	}

	/**
	 * Build the request params for multiple tracks
	 * 
	 * @param trackIds
	 * @param market
	 * @return
	 */
	private StringBuilder getTrackParams(List<String> trackIds, String market) {
		StringBuilder trackParams = new StringBuilder();
		trackParams.append("?ids=");
		int count = 0;
		for (String id : trackIds) {
			if (count == 0) {
				trackParams.append(id);
				count++;
			} else {
				trackParams.append("%" + id);
			}
		}
		trackParams.append("&market=" + market);
		return trackParams;
	}

	/**
	 * Create a curl Get request for the Spotify Track endpoints: tracks/{id} and
	 * tracks (with id parameters)
	 * 
	 * @param token
	 * @param trackId
	 * @return
	 */
	private HttpURLConnection getConnection(String token, String trackId) {
		try {
			URL obj = new URL(trackURI + trackId);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "Bearer " + token);
			con.setRequestProperty("Accept", "application/json");
			System.out.println("GET Response Code :: " + con.getResponseCode());
			return con;
		} catch (IOException e) {
			System.out.println("*** TRACK REQUEST ERROR ***");
			e.printStackTrace();
			return null;
		}
	}

	// Artists
	// 2CIMQHirSU0MQqyYHq0eOx
	// 57dN52uHvrHOxijzpIgu3E
	// 1vCWHaC5f2uS3yhpwWbIA6
	
	// Tracks
	// 2nLgWMdYPO35GGpwX2xo23
	// 4ua0IepBEISCWwF8dTJvcU
	// 1ROBixGgXrYlcCcrBfxAoy
	// 77w8cBOdasP7aNcPD9Dec8
	// 05OUNj7yQ6TBTQ4YZib8zv
	// 6c9EGVj5CaOeoKd9ecMW1U
	// 6Qng1hawspj0ddyexe0IHV
	// 5eXAwRzk8mBnLilhGLVLOF
	// 2vPlEZrLXYhZkMzEFyfroi
	// 2edcAWwKM7SQajsFGP0edC
	
}
