package com.mfinder.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RatingEvent.
 */
@Entity
@Table(name = "rating_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingEvent extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "comment")
    private String comment;

    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    @Column(name = "rating", nullable = false)
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RatingEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return this.comment;
    }

    public RatingEvent comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return this.rating;
    }

    public RatingEvent rating(Double rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public byte[] getImage() {
        return this.image;
    }

    public RatingEvent image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public RatingEvent imageContentType(String imageContentType) {
        this.setImageContentType(imageContentType);
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    // public User getAuthor() {
    //     return author;
    // }

    // public void setAuthor(User author) {
    //     this.author = author;
    // }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatingEvent)) {
            return false;
        }
        return id != null && id.equals(((RatingEvent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", rating=" + getRating() + "'" +
            ", event=" + getEvent() + "'" +
            ", image='" + getImage() +
            "}";
    }
}
