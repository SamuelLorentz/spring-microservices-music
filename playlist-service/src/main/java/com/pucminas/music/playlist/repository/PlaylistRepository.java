package com.pucminas.music.playlist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pucminas.music.playlist.model.Playlist;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

}
