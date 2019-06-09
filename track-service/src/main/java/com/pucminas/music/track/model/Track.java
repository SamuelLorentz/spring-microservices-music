package com.pucminas.music.track.model;

import java.util.Arrays;
import java.util.List;

public class Track {

	private String id;
	private String name;
	private Artist[] artists;
	private Album album;
	private String type;
	private String[] available_markets;
	private Integer disc_number;
	private Integer duration_ms;
	private Boolean explicit;
	private ExternalUrl external_urls;
	private String href;
	private Boolean is_playable;
	private TrackLink linked_from;
	private List<Restrictions> restrictions;
	private String preview_url;
	private Integer track_number;
	private String uri;
	private Boolean is_local;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Artist[] getArtists() {
		return artists;
	}

	public void setArtists(Artist[] artists) {
		this.artists = artists;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getAvailable_markets() {
		return available_markets;
	}

	public void setAvailable_markets(String[] available_markets) {
		this.available_markets = available_markets;
	}

	public Integer getDisc_number() {
		return disc_number;
	}

	public void setDisc_number(Integer disc_number) {
		this.disc_number = disc_number;
	}

	public Integer getDuration_ms() {
		return duration_ms;
	}

	public void setDuration_ms(Integer duration_ms) {
		this.duration_ms = duration_ms;
	}

	public Boolean getExplicit() {
		return explicit;
	}

	public void setExplicit(Boolean explicit) {
		this.explicit = explicit;
	}

	public ExternalUrl getExternal_urls() {
		return external_urls;
	}

	public void setExternal_urls(ExternalUrl external_urls) {
		this.external_urls = external_urls;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Boolean getIs_playable() {
		return is_playable;
	}

	public void setIs_playable(Boolean is_playable) {
		this.is_playable = is_playable;
	}

	public TrackLink getLinked_from() {
		return linked_from;
	}

	public void setLinked_from(TrackLink linked_from) {
		this.linked_from = linked_from;
	}

	public List<Restrictions> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<Restrictions> restrictions) {
		this.restrictions = restrictions;
	}

	public String getPreview_url() {
		return preview_url;
	}

	public void setPreview_url(String preview_url) {
		this.preview_url = preview_url;
	}

	public Integer getTrack_number() {
		return track_number;
	}

	public void setTrack_number(Integer track_number) {
		this.track_number = track_number;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Boolean getIs_local() {
		return is_local;
	}

	public void setIs_local(Boolean is_local) {
		this.is_local = is_local;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", name=" + name + ", artists=" + Arrays.toString(artists) + ", album=" + album
				+ ", type=" + type + ", available_markets=" + Arrays.toString(available_markets) + ", disc_number="
				+ disc_number + ", duration_ms=" + duration_ms + ", explicit=" + explicit + ", external_urls="
				+ external_urls + ", href=" + href + ", is_playable=" + is_playable + ", linked_from=" + linked_from
				+ ", restrictions=" + restrictions + ", preview_url=" + preview_url + ", track_number=" + track_number
				+ ", uri=" + uri + ", is_local=" + is_local + "]";
	}

}
