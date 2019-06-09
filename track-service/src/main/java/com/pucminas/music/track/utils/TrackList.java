package com.pucminas.music.track.utils;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.pucminas.music.track.model.Track;

public class TrackList {

	@SerializedName("tracks")
	private List<Track> tracks;

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
}
