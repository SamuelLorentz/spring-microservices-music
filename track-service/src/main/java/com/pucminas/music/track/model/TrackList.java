package com.pucminas.music.track.model;

import com.pucminas.music.track.model.Track;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackList {

	private List<Track> tracks;
}