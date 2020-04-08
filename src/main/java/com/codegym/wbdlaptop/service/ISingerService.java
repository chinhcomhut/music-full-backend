package com.codegym.wbdlaptop.service;

import com.codegym.wbdlaptop.model.Singer;


import java.util.List;
import java.util.Optional;

public interface ISingerService {
  List<Singer> findAll();
  List<Singer> findAllByUserId(Long userId);
  Singer findByIdSinger (Long id);
  void save (Singer singer);
  void delete (Long id);
}
