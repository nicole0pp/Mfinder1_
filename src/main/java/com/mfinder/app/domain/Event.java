package com.mfinder.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mfinder.app.domain.enumeration.City;
import com.mfinder.app.domain.enumeration.TipoEvento;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEvento tipoEvento;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "location")
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "city", nullable = false)
    private City city;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "seating_capacity", nullable = false)
    private Integer seatCapacity;

    @ManyToMany
    @JoinTable(name = "artist_event", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "events" }, allowSetters = true)
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "rating_event",
        joinColumns = { @JoinColumn(name = "event_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "rating_id", referencedColumnName = "id") }
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Rating> ratings = new HashSet<>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Event name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Event image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Event imageContentType(String imageContentType) {
        this.setImageContentType(imageContentType);
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public TipoEvento getTipoEvento() {
        return this.tipoEvento;
    }

    public Event tipoEvento(TipoEvento tipoEvento) {
        this.setTipoEvento(tipoEvento);
        return this;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Event startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Event endEvent(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return this.location;
    }

    public Event location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public City getCity() {
        return this.city;
    }

    public Event city(City city) {
        this.setCity(city);
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeatCapacity() {
        return this.seatCapacity;
    }

    public Event seatCapacity(Integer seatCapacity) {
        this.setDescription(description);
        return this;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Set<Artist> getArtists() {
        return this.artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Event artists(Set<Artist> artists) {
        this.setArtists(artists);
        return this;
    }

    public Event addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getEvents().add(this);
        return this;
    }

    public Event removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.getEvents().remove(this);
        return this;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
        "id=" + getId() +
        ", name='" + getName() + "'" +
        ", picture='" + getImage() + "'" +
        ", tipoEvento='" + getTipoEvento() + "'" +
        ", startDate='" + getStartDate() + "'" +
        ", endDate='" + getEndDate() + "'" +
        ", location='" + getLocation() + "'" +
        ", city='" + getCity() + "'" +
        ", description='" + getDescription() + "'" +
        "}"; 
    }
}
