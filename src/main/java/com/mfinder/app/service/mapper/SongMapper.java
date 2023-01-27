package com.mfinder.app.service.mapper;

import com.mfinder.app.domain.Album;
import com.mfinder.app.domain.ListDetails;
import com.mfinder.app.domain.Song;
import com.mfinder.app.service.dto.AlbumDTO;
import com.mfinder.app.service.dto.ListDetailsDTO;
import com.mfinder.app.service.dto.SongDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Song} and its DTO {@link SongDTO}.
 */
@Mapper(componentModel = "spring")
public interface SongMapper extends EntityMapper<SongDTO, Song> {
    @Mapping(target = "listDetails", source = "listDetails", qualifiedByName = "listDetailsId")
    @Mapping(target = "album", source = "album", qualifiedByName = "albumId")
    SongDTO toDto(Song s);

    @Named("listDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ListDetailsDTO toDtoListDetailsId(ListDetails listDetails);

    @Named("albumId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlbumDTO toDtoAlbumId(Album album);
}
