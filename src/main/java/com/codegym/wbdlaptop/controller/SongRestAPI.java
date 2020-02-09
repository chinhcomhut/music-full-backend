package com.codegym.wbdlaptop.controller;


import com.codegym.wbdlaptop.model.Song;
import com.codegym.wbdlaptop.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class SongRestAPI {

    @Autowired
    private ISongService songService;

    @GetMapping("/song/pagination")
    public ResponseEntity<?> getListProductAndPagination(@PageableDefault(value = 2 , sort = "date" ,direction = Sort.Direction.ASC) Pageable pageable) {
//        DESC = Old , ASC = new
        Page<Song> products =  songService.findAll(pageable);

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/song")
    public ResponseEntity<?> getListProduct() {
        List<Song> songs = (List<Song>) songService.findAll();
        if(songs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(songs,HttpStatus.OK);
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Optional<Song> product = songService.findById(id);

        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/song")
    public ResponseEntity<?> createSong(@Valid @RequestBody Song song) {

        song.setUpdate(false);
        songService.save(song);

        return new ResponseEntity<>(song, HttpStatus.CREATED);
    }

    @PutMapping("/song/{id}")
    public ResponseEntity<?> updateSong(@Valid @RequestBody Song song, @PathVariable Long id) {
        Optional<Song> song1 = songService.findById(id);

        if (!song1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        song1.get().setNameSong(song.getNameSong());
        song1.get().setLyrics(song.getLyrics());
        song1.get().setMp3Url(song.getMp3Url());
        song1.get().setSinger(song.getSinger());
        song1.get().setUser(song.getUser());
        song1.get().setUpdate(true);


        songService.save(song1.get());

        return new ResponseEntity<>(song1, HttpStatus.OK);
    }

    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Song> song = songService.findById(id);

        if (!song.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        songService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/song/search-by-name")
//    public ResponseEntity<?> searchProductByName(@RequestBody SearchProductByNameForm nameForm) {
//        if (nameForm.getName() == "" || nameForm.getName() == null ) {
//            List<Song> songs = (List<Song>) songService.findAll();
//
//            if(songs.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            } else {
//                return new ResponseEntity<>(songs,HttpStatus.OK);
//            }
//        }
//
//        List<Song> songs = (List<Song>) songService.findProductsByNameContaining(nameForm.getName());
//        if(songs.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(songs,HttpStatus.OK);
//        }
//    }
//
//    @GetMapping("/product/search-by-lineId/{id}")
//    public ResponseEntity<?> searchByLineId(@PathVariable Long id) {
//        List<Song> songs = (List<Song>) productService.findProductsByLineId(id);
//
//        if (songs.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(songs,HttpStatus.OK);
//    }
//
//    @PostMapping("/product/search-by-line-and-name")
//    public ResponseEntity<?> searchProductByLineAndName(@RequestBody SearchProductByLineAndName searchForm) {
//        if (searchForm.getName() == null && searchForm.getLineId() == null) {
//            List<Song> songs = (List<Song>) productService.findAll();
//            if(songs.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(songs,HttpStatus.OK);
//        }
//
//        if (searchForm.getName() == null && searchForm.getLineId() != null) {
//            List<Song> songs = (List<Song>) productService.findProductsByLineId(searchForm.getLineId());
//            if(songs.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(songs,HttpStatus.OK);
//        }
//
//        if (searchForm.getName() != null && searchForm.getLineId() == null) {
//            List<Song> songs = (List<Song>) productService.findProductsByNameContaining(searchForm.getName());
//            if(songs.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(songs,HttpStatus.OK);
//        }
//
//        if (searchForm.getLineId() != null && searchForm.getName() != null) {
//            List<Song> songs = (List<Song>) productService.findProductsByLineIdAndNameContaining(searchForm.getLineId(),searchForm.getName());
//            if(songs.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(songs,HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
