package com.pucminas.music.track.utils;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.pucminas.music.track.model.Artist;

public class ArtistList {

	@SerializedName("artists")
	private List<Artist> artists;

	public List<Artist> getTracks() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
	
}
