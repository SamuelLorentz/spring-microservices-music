package com.pucminas.music.playlist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackDTO {

	private Long id;
	private String track;
	private String name;
	private String type;
}