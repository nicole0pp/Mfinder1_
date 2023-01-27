package com.mfinder.app.service.dto;

import com.mfinder.app.domain.enumeration.MusicGenre;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mfinder.app.domain.Song} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SongDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private byte[] picture;

    private String pictureContentType;
    private Duration duration;

    @Lob
    private byte[] audio;

    private String audioContentType;
    private String artists;

    private MusicGenre musicGenre;

    private ListDetailsDTO listDetails;

    private AlbumDTO album;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getAudioContentType() {
        return audioContentType;
    }

    public void setAudioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
    }

    public ListDetailsDTO getListDetails() {
        return listDetails;
    }

    public void setListDetails(ListDetailsDTO listDetails) {
        this.listDetails = listDetails;
    }

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SongDTO)) {
            return false;
        }

        SongDTO songDTO = (SongDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, songDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SongDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", picture='" + getPicture() + "'" +
            ", duration='" + getDuration() + "'" +
            ", audio='" + getAudio() + "'" +
            ", artists='" + getArtists() + "'" +
            ", musicGenre='" + getMusicGenre() + "'" +
            ", listDetails=" + getListDetails() +
            ", album=" + getAlbum() +
            "}";
    }
}
