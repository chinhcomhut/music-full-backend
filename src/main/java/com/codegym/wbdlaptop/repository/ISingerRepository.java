package com.codegym.wbdlaptop.repository;

import com.codegym.wbdlaptop.model.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISingerRepository extends JpaRepository<Singer, Long> {
List<Singer> findAllByUserId(Long userId);

}
