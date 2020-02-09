package com.codegym.wbdlaptop.repository;

import com.codegym.wbdlaptop.model.Song;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {
//    Iterable<Song> findProductsByUserId(Long user_id);
    Iterable<Song> findSongsByUserId(Long user_id);
//    Iterable<Song> findProductsByNameContaining(String name);
    Iterable<Song> findSongsByNameSongContaining(String nameSong);
//    Iterable<Song> findProductsByLineId(Long line_id);
    Iterable<Song> findSongsBySingerId(Long singer_id);
//    Iterable<Song> findProductsByLineIdAndNameContaining(Long line_id, String name);
    Iterable<Song> findSongsBySingerIdAndNameSongContaining(Long singer_id, String nameSong);
}
