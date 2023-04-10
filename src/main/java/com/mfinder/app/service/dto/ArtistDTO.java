package com.mfinder.app.service.dto;

import com.mfinder.app.domain.User;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mfinder.app.domain.Artist} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtistDTO implements Serializable {

    private Long id;

    private String artistName;

    private User user;

    private String insta_link;

    private String spoti_link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInsta_link() {
        return insta_link;
    }

    public void setInsta_link(String insta_link) {
        this.insta_link = insta_link;
    }

    public String getSpoti_link() {
        return spoti_link;
    }

    public void setSpoti_link(String spoti_link) {
        this.spoti_link = spoti_link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtistDTO)) {
            return false;
        }

        ArtistDTO artistDTO = (ArtistDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artistDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistDTO{" +
            "id=" + getId() +
            "user_id='" + getUser() + "'" +
            ", artistName='" + getArtistName() + "'" +
            ", insta_link='" + getInsta_link() + "'" +
            ", spoti_link='" + getSpoti_link() + "'" +
            "}";
    }
}
