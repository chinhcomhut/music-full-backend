package com.codegym.wbdlaptop.service.Impl;

import com.codegym.wbdlaptop.model.Singer;
import com.codegym.wbdlaptop.repository.ISingerRepository;
import com.codegym.wbdlaptop.service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingerServiceImpl implements ISingerService {
    @Autowired
    private ISingerRepository singerRepository;


    @Override
    public List<Singer> findAll() {
        return singerRepository.findAll();
    }

    @Override
    public List<Singer> findAllByUserId(Long userId) {
        return singerRepository.findAllByUserId(userId);
    }

    @Override
    public Singer findByIdSinger(Long id) {
        return null;
    }

    @Override
    public void save(Singer singer) {

    }

    @Override
    public void delete(Long id) {

    }
}
