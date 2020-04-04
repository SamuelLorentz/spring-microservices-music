package com.pucminas.music.playlist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pucminas.music.playlist.model.Track;

@Repository
public interface TrackRepository extends CrudRepository<Track, String> {

	 Track findByTrackId(String trackId);
}
