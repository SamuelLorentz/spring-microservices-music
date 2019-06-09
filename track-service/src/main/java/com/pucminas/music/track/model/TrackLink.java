package com.pucminas.music.track.model;

public class TrackLink {

	private String id;
	private String type;
	private String href;
	private String uri;
	private ExternalUrl external_urls;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
