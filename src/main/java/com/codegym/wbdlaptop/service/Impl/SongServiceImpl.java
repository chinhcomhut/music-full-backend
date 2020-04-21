package com.codegym.wbdlaptop.service.Impl;

import com.codegym.wbdlaptop.model.Song;
import com.codegym.wbdlaptop.repository.ISongRepository;
import com.codegym.wbdlaptop.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements ISongService {
    @Autowired
    private ISongRepository songRepository;


    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public Iterable<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Page<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void delete(Long id) {
songRepository.deleteById(id);
    }

    @Override
    public Iterable<Song> findSongsByUserId(Long user_id) {
        return songRepository.findSongsByUserId(user_id);
    }

    @Override
    public Iterable<Song> findSongsByNameSongContaining(String name) {
        return findSongsByNameSongContaining(name);
    }

    @Override
    public Iterable<Song> findSongsBySingerId(Long singer_id) {
        return songRepository.findSongsBySingerId(singer_id);
    }

    @Override
    public Iterable<Song> findSongsBySingerAndNameSongContaining(Long singer_id, String name) {
        return songRepository.findSongsBySingerAndNameSongContaining(singer_id, name);
    }
}
