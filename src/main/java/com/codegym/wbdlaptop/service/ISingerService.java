package com.codegym.wbdlaptop.service;

import com.codegym.wbdlaptop.model.Singer;
import javafx.collections.ObservableSet;

import java.util.List;
import java.util.Optional;

public interface ISingerService {
    Optional<Singer> findById(Long id);

    Iterable<Singer> findAll();

    Singer save(Singer singer);

    void delete(Long id);

    Iterable<Singer> findSingersByNameSingerContaining(String singer_name);

    Iterable<Singer> findAllByUserId(Long userId);

    Iterable<Singer> findSingersByUserId(Long userId);
}
