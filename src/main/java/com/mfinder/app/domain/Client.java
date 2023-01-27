package com.mfinder.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "listDetails", "artist", "client" }, allowSetters = true)
    private Set<FavoriteList> favoriteLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FavoriteList> getFavoriteLists() {
        return this.favoriteLists;
    }

    public void setFavoriteLists(Set<FavoriteList> favoriteLists) {
        if (this.favoriteLists != null) {
            this.favoriteLists.forEach(i -> i.setClient(null));
        }
        if (favoriteLists != null) {
            favoriteLists.forEach(i -> i.setClient(this));
        }
        this.favoriteLists = favoriteLists;
    }

    public Client favoriteLists(Set<FavoriteList> favoriteLists) {
        this.setFavoriteLists(favoriteLists);
        return this;
    }

    public Client addFavoriteList(FavoriteList favoriteList) {
        this.favoriteLists.add(favoriteList);
        favoriteList.setClient(this);
        return this;
    }

    public Client removeFavoriteList(FavoriteList favoriteList) {
        this.favoriteLists.remove(favoriteList);
        favoriteList.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            "}";
    }
}
