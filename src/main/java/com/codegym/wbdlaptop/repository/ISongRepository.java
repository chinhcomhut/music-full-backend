package com.codegym.wbdlaptop.repository;

import com.codegym.wbdlaptop.model.Song;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {
Iterable<Song> findSongsByUserId(Long user_id);
Iterable<Song> findSongsByNameSongContaining(String name);
Iterable<Song> findSongsBySingerId(Long singer_id);
Iterable<Song> findSongsBySingerAndNameSongContaining(Long singer_id, String name);
}
