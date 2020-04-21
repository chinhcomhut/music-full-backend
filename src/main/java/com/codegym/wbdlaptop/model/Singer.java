package com.codegym.wbdlaptop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "singer")
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameSinger;
    private String information;
    private String avatarSinger;
@OneToOne
@JoinColumn
private User user;
//    @JsonIgnore
//    @OneToMany(targetEntity = Song.class, mappedBy = "singer", cascade = CascadeType.ALL)

  @JsonIgnore
  @OneToMany(targetEntity = Song.class, mappedBy = "singer", cascade = CascadeType.ALL)
    private List<Song> songs;
    public Singer() {
    }

    public Singer(String nameSinger, String information, String avatarSinger, User user, List<Song> songs) {
        this.nameSinger = nameSinger;
        this.information = information;
        this.avatarSinger = avatarSinger;
        this.user = user;
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getAvatarSinger() {
        return avatarSinger;
    }

    public void setAvatarSinger(String avatarSinger) {
        this.avatarSinger = avatarSinger;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
