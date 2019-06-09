package com.pucminas.music.track.model;

public class Album {

	private String album_type;
	private Artist[] artists;
	private String[] available_markets;
	private Copyrights copyrights;
	private ExternalUrl external_ids;
	private Image[] images;
	private ExternalUrl external_urls;
	private String[] genres;
	private String href;
	private String id;
	private String label;
	private String name;
	private Integer popularity;
	private String release_date;
	private String release_date_precision;
	private Restrictions restrictions;
	private Track[] tracks;
	private String type;
	private String uri;

	public String getAlbum_type() {
		return album_type;
	}

	public void setAlbum_type(String album_type) {
		this.album_type = album_type;
	}

	public Artist[] getArtists() {
		return artists;
	}

	public void setArtists(Artist[] artists) {
		this.artists = artists;
	}

	public String[] getAvailable_markets() {
		return available_markets;
	}

	public void setAvailable_markets(String[] available_markets) {
		this.available_markets = available_markets;
	}

	public Copyrights getCopyrights() {
		return copyrights;
	}

	public void setCopyrights(Copyrights copyrights) {
		this.copyrights = copyrights;
	}

	public ExternalUrl getExternal_ids() {
		return external_ids;
	}

	public void setExternal_ids(ExternalUrl external_ids) {
		this.external_ids = external_ids;
	}

	public ExternalUrl getExternal_urls() {
		return external_urls;
	}

	public void setExternal_urls(ExternalUrl external_urls) {
		this.external_urls = external_urls;
	}

	public String[] getGenres() {
		return genres;
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPopularity() {
		return popularity;
	}

	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getRelease_date_precision() {
		return release_date_precision;
	}

	public void setRelease_date_precision(String release_date_precision) {
		this.release_date_precision = release_date_precision;
	}

	public Restrictions getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Restrictions restrictions) {
		this.restrictions = restrictions;
	}

	public Track[] getTracks() {
		return tracks;
	}

	public void setTracks(Track[] tracks) {
		this.tracks = tracks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Image[] getImages() {
		return images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}

}
