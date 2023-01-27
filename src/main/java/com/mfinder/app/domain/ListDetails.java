package com.mfinder.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ListDetails.
 */
@Entity
@Table(name = "list_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ListDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "listDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "listDetails", "artist", "client" }, allowSetters = true)
    private Set<FavoriteList> lists = new HashSet<>();

    @OneToMany(mappedBy = "listDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "listDetails", "album" }, allowSetters = true)
    private Set<Song> songs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ListDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FavoriteList> getLists() {
        return this.lists;
    }

    public void setLists(Set<FavoriteList> favoriteLists) {
        if (this.lists != null) {
            this.lists.forEach(i -> i.setListDetails(null));
        }
        if (favoriteLists != null) {
            favoriteLists.forEach(i -> i.setListDetails(this));
        }
        this.lists = favoriteLists;
    }

    public ListDetails lists(Set<FavoriteList> favoriteLists) {
        this.setLists(favoriteLists);
        return this;
    }

    public ListDetails addList(FavoriteList favoriteList) {
        this.lists.add(favoriteList);
        favoriteList.setListDetails(this);
        return this;
    }

    public ListDetails removeList(FavoriteList favoriteList) {
        this.lists.remove(favoriteList);
        favoriteList.setListDetails(null);
        return this;
    }

    public Set<Song> getSongs() {
        return this.songs;
    }

    public void setSongs(Set<Song> songs) {
        if (this.songs != null) {
            this.songs.forEach(i -> i.setListDetails(null));
        }
        if (songs != null) {
            songs.forEach(i -> i.setListDetails(this));
        }
        this.songs = songs;
    }

    public ListDetails songs(Set<Song> songs) {
        this.setSongs(songs);
        return this;
    }

    public ListDetails addSong(Song song) {
        this.songs.add(song);
        song.setListDetails(this);
        return this;
    }

    public ListDetails removeSong(Song song) {
        this.songs.remove(song);
        song.setListDetails(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListDetails)) {
            return false;
        }
        return id != null && id.equals(((ListDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListDetails{" +
            "id=" + getId() +
            "}";
    }
}
