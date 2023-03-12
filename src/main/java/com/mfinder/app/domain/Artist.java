package com.mfinder.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Artist.
 */
@Entity
@Table(name = "artist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "artistic_name", unique = true)
    private String artistName;

    @OneToMany(mappedBy = "atist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sngs", "atist" }, allowSetters = true)
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "listDetails", "artist", "client" }, allowSetters = true)
    private Set<FavoriteList> favoriteLists = new HashSet<>();

    //Relacion con el User
    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artist id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(Set<Album> albums) {
        if (this.albums != null) {
            this.albums.forEach(i -> i.setAtist(null));
        }
        if (albums != null) {
            albums.forEach(i -> i.setAtist(this));
        }
        this.albums = albums;
    }

    public Artist albums(Set<Album> albums) {
        this.setAlbums(albums);
        return this;
    }

    public Artist addAlbum(Album album) {
        this.albums.add(album);
        album.setAtist(this);
        return this;
    }

    public Artist removeAlbum(Album album) {
        this.albums.remove(album);
        album.setAtist(null);
        return this;
    }

    public Set<FavoriteList> getFavoriteLists() {
        return this.favoriteLists;
    }

    public void setFavoriteLists(Set<FavoriteList> favoriteLists) {
        if (this.favoriteLists != null) {
            this.favoriteLists.forEach(i -> i.setArtist(null));
        }
        if (favoriteLists != null) {
            favoriteLists.forEach(i -> i.setArtist(this));
        }
        this.favoriteLists = favoriteLists;
    }

    public Artist favoriteLists(Set<FavoriteList> favoriteLists) {
        this.setFavoriteLists(favoriteLists);
        return this;
    }

    public Artist addFavoriteList(FavoriteList favoriteList) {
        this.favoriteLists.add(favoriteList);
        favoriteList.setArtist(this);
        return this;
    }

    public Artist removeFavoriteList(FavoriteList favoriteList) {
        this.favoriteLists.remove(favoriteList);
        favoriteList.setArtist(null);
        return this;
    }

    //Relacion con el User

    public User getUser() {
        return user;
    }

    public Artist user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artist)) {
            return false;
        }
        return id != null && id.equals(((Artist) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artist{" +
            "id=" + getId() +
            ", user_id=" + getUser() +
            "}";
    }
}
