package com.codegym.wbdlaptop.service;

import com.codegym.wbdlaptop.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISongService {
    Optional<Song> findById(Long id);

    Iterable<Song> findAll();

    Page<Song> findAll(Pageable pageable);

    Song save(Song song);

    void delete(Long id);

    Iterable<Song> findSongsByUserId(Long user_id);

    Iterable<Song> findSongsByNameSongContaining(String name);

    Iterable<Song> findSongsBySingerId(Long singer_id);

    Iterable<Song> findSongsBySingerAndNameSongContaining(Long singer_id, String name);
}
