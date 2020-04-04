package com.pucminas.music.track.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album {

	private Artist[] artists;
	private Image[] images;
	private Track[] tracks;

	private Copyrights copyrights;
	private ExternalUrl external_ids;
	private ExternalUrl external_urls;
	private Restrictions restrictions;

	private String[] genres;
	private String[] available_markets;

	private Integer popularity;
	private String album_type;
	private String href;
	private String id;
	private String label;
	private String name;
	private String release_date;
	private String release_date_precision;
	private String type;
	private String uri;
}