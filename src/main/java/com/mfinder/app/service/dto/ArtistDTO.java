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

    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
            "user_id=" + getUser() +
            "}";
    }
}
