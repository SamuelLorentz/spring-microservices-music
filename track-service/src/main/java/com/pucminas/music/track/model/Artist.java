package com.pucminas.music.track.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

	private String id;
	private String name;
	private String type;
	private String uri;
	private String href;
	private ExternalUrl external_urls;
}