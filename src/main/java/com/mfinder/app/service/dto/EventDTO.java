package com.mfinder.app.service.dto;

import com.mfinder.app.domain.enumeration.City;
import com.mfinder.app.domain.enumeration.TipoEvento;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.mfinder.app.domain.Event} entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private byte[] image;

    private String imageContentType;

    @NotNull
    private TipoEvento tipoEvento;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    private String location;

    @NotNull
    private City city;

    private String description;

    private Set<String> artists;

    @NotNull
    private Integer seatCapacity;

    private String createdBy;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getArtist() {
        return artists;
    }

    public void setArtist(Set<String> artists) {
        this.artists = artists;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }
        EventDTO eventDTO = (EventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "EventDTO{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", picture='" +
            getImage() +
            "'" +
            ", tipoEvento='" +
            getTipoEvento() +
            "'" +
            ", startDate='" +
            getStartDate() +
            ", endDate='" +
            getEndDate() +
            "'" +
            ", location='" +
            getLocation() +
            "'" +
            ", city='" +
            getCity() +
            "'" +
            ", description='" +
            getDescription() +
            "'" +
            ", artists='" +
            getArtist() +
            "'" +
            "}"
        );
    }
}
