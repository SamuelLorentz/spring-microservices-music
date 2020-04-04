package com.pucminas.music.track.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {

	private Album album;
	private TrackLink linkedFrom;
	private ExternalUrl externalUrls;

	private List<Artist> artists;
	private List<Restrictions> restrictions;
	private List<String> availableMarkets;

	private Integer trackNumber;
	private Integer discNumber;
	private Integer durationMs;
	private String id;
	private String name;
	private String type;
	private String href;
	private String previewUrl;
	private String uri;
	private Boolean explicit;
	private Boolean playable;
	private Boolean local;
}