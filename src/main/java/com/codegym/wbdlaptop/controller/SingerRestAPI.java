package com.codegym.wbdlaptop.controller;


import com.codegym.wbdlaptop.message.response.ResponseMessage;
import com.codegym.wbdlaptop.model.Singer;
import com.codegym.wbdlaptop.model.Song;
import com.codegym.wbdlaptop.security.service.UserPrinciple;
import com.codegym.wbdlaptop.service.ISingerService;
import com.codegym.wbdlaptop.service.ISongService;
import com.codegym.wbdlaptop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class SingerRestAPI {
    @Autowired
    private ISingerService singerService;

    @Autowired
    private IUserService userService;

    private UserPrinciple getCurrentUser(){
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PutMapping("/singer{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateSinger(@Valid @RequestBody Singer singer, @PathVariable("id") Long id) {
        Singer singer1 = singerService.findByIdSinger(id);
        singer1.setNameSinger(singer.getNameSinger());
        singer1.setInformation(singer.getInformation());
        singer1.setAvatarSinger(singer.getAvatarSinger());
        singer1.setSongs(singer.getSongs());
        singer1.setUser(singer.getUser());
        singerService.save(singer1);
        return new ResponseEntity<>(singer1, HttpStatus.OK);
    }

    @PostMapping("/singer")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> createSinger(@Valid @RequestBody Singer singer) {
        singer.setUser(this.userService.findById(getCurrentUser().getId()));
        singerService.save(singer);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("create singer successfully",null), HttpStatus.OK);
    }

    @GetMapping("/singer")
    public ResponseEntity<?> allSinger(){
        List<Singer> singers = singerService.findAll();
        return new ResponseEntity<>(singers,HttpStatus.OK);
    }

    @GetMapping("/singer{id}")
    public ResponseEntity<?> getByIdSinger(@PathVariable("id") Long id) {
        Singer singers = singerService.findByIdSinger(id);
        return new ResponseEntity<>(singers,HttpStatus.OK);
    }

    @DeleteMapping("/singer{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSinger(@PathVariable("id") Long id){
        singerService.delete(id);
        return new ResponseEntity<>(new ResponseMessage("delete success"),HttpStatus.OK);
    }
}
