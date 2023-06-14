package com.mfinder.app.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FavoriteList.
 */
@Entity
@Table(name = "favorite_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FavoriteList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @OneToMany(mappedBy = "favoriteList", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ListDetails> listDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FavoriteList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FavoriteList name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public FavoriteList picture(byte[] picture) {
        this.setPicture(picture);
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return this.pictureContentType;
    }

    public FavoriteList pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public List<ListDetails> getListDetails() {
        return this.listDetails;
    }

    public void setListDetails(List<ListDetails> listDetails) {
        if (this.listDetails != null) {
            this.listDetails.forEach(i -> i.setFavoriteList(null));
        }
        if (listDetails != null) {
            listDetails.forEach(i -> i.setFavoriteList(this));
        }
        this.listDetails = listDetails;
    }

    public FavoriteList listDetails(List<ListDetails> listDetails) {
        this.setListDetails(listDetails);
        return this;
    }

    public FavoriteList addListDetails(ListDetails listDetails) {
        this.listDetails.add(listDetails);
        listDetails.setFavoriteList(this);
        return this;
    }

    public FavoriteList removeListDetails(ListDetails ListDetails) {
        this.listDetails.remove(ListDetails);
        ListDetails.setFavoriteList(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavoriteList)) {
            return false;
        }
        return id != null && id.equals(((FavoriteList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavoriteList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            "}";
    }
}
