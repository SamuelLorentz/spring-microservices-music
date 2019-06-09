package com.pucminas.music.track.model.dto;

public class TrackDTO {

	private Long id;
	private String track;
	private String name;
	private String type;

	public TrackDTO() {
	}

	public TrackDTO(String track, String name, String type) {
		super();
		this.id = (long) 1;
		this.track = track;
		this.name = name;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

}
