package com.codegym.wbdlaptop.controller;


import com.codegym.wbdlaptop.message.response.ResponseMessage;
import com.codegym.wbdlaptop.model.Singer;
import com.codegym.wbdlaptop.model.Song;
import com.codegym.wbdlaptop.service.ISingerService;
import com.codegym.wbdlaptop.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ISongService songService;

    @GetMapping("/singer")
    public ResponseEntity<?> getListAllLine() {
        List<Singer> singerList = (List<Singer>) singerService.findAll();

        if(singerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(singerList,HttpStatus.OK);
    }

    @GetMapping("/singer/{id}")
    public ResponseEntity<?> getSinger(@PathVariable Long id) {
        Optional<Singer> line = singerService.findById(id);

        if (!line.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(line,HttpStatus.OK);
    }

    @PostMapping("/singer")
    public ResponseEntity<?> createSinger(@Valid @RequestBody Singer singer) {
        singerService.save(singer);

        return new ResponseEntity<>(singer,HttpStatus.CREATED);
    }

    @PutMapping("/singer" + "/{id}")
    public ResponseEntity<?> updateSinger(@Valid @RequestBody Singer singer, @PathVariable Long id) {
        Optional<Singer> singer1 = singerService.findById(id);
        if(!singer1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        singer1.get().setNameSinger(singer.getNameSinger());
        singer1.get().setUser(singer.getUser());
        singer1.get().setInformation(singer.getInformation());
        singer1.get().setAvatarSinger(singer.getAvatarSinger());
        singer1.get().setSongs(singer.getSongs());
        singerService.save(singer1.get());

        return new ResponseEntity<>(singer1,HttpStatus.OK);
    }

//    @DeleteMapping("/singer/{id}")
//    public ResponseEntity<?> deleteSinger(@PathVariable Long id) {
//        Optional<Singer> singer = singerService.findById(id);
//        if(!singer.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        List<Song> songs = (List<Song>) songService.findSongByUserId(id);
//
//        if(!songs.isEmpty()) {
//            for (Song song : songs) {
//                songService.delete(song.getId());
//            }
//        }
//
//        singerService.delete(id);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
@DeleteMapping("/delete/{id}")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public ResponseEntity<?> deleteSinger(@PathVariable("id") Long id){
    singerService.delete(id);
    return new ResponseEntity<>(new ResponseMessage("delete success"),HttpStatus.OK);
}

//    @PostMapping("/line/search-by-name")
//    public ResponseEntity<?> searchLineByNameLine(@RequestBody SearchLineByNameLine lineForm) {
//        if(lineForm.getName() == "" || lineForm.getName() == null ) {
//            List<Singer> singers = (List<Singer>) lineService.findAll();
//            if(singers.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            } else {
//                return new ResponseEntity<>(singers,HttpStatus.OK);
//            }
//        }
//
//        List<Singer> singers = (List<Singer>) lineService.findLinesByNameContaining(lineForm.getName());
//        if(singers.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(singers,HttpStatus.OK);
//        }
//    }
}
