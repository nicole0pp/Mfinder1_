package com.mfinder.app.service.mapper;

import com.mfinder.app.domain.Album;
import com.mfinder.app.domain.Artist;
import com.mfinder.app.service.dto.AlbumDTO;
import com.mfinder.app.service.dto.ArtistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Album} and its DTO {@link AlbumDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlbumMapper extends EntityMapper<AlbumDTO, Album> {
    @Mapping(target = "atist", source = "atist", qualifiedByName = "artistId")
    AlbumDTO toDto(Album s);

    @Named("artistId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtistDTO toDtoArtistId(Artist artist);
}
