package com.mfinder.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mfinder.app.domain.FavoriteList} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FavoriteListDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private byte[] picture;

    private String pictureContentType;
    private ListDetailsDTO listDetails;

    private ArtistDTO artist;

    private ClientDTO client;

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

    public ListDetailsDTO getListDetails() {
        return listDetails;
    }

    public void setListDetails(ListDetailsDTO listDetails) {
        this.listDetails = listDetails;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavoriteListDTO)) {
            return false;
        }

        FavoriteListDTO favoriteListDTO = (FavoriteListDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, favoriteListDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavoriteListDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", picture='" + getPicture() + "'" +
            ", listDetails=" + getListDetails() +
            ", artist=" + getArtist() +
            ", client=" + getClient() +
            "}";
    }
}
