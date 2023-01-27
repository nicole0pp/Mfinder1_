package com.mfinder.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mfinder.app.domain.ListDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ListDetailsDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListDetailsDTO)) {
            return false;
        }

        ListDetailsDTO listDetailsDTO = (ListDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, listDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListDetailsDTO{" +
            "id=" + getId() +
            "}";
    }
}
