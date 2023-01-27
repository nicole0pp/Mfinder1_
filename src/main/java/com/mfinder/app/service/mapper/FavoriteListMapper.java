package com.mfinder.app.service.mapper;

import com.mfinder.app.domain.Artist;
import com.mfinder.app.domain.Client;
import com.mfinder.app.domain.FavoriteList;
import com.mfinder.app.domain.ListDetails;
import com.mfinder.app.service.dto.ArtistDTO;
import com.mfinder.app.service.dto.ClientDTO;
import com.mfinder.app.service.dto.FavoriteListDTO;
import com.mfinder.app.service.dto.ListDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FavoriteList} and its DTO {@link FavoriteListDTO}.
 */
@Mapper(componentModel = "spring")
public interface FavoriteListMapper extends EntityMapper<FavoriteListDTO, FavoriteList> {
    @Mapping(target = "listDetails", source = "listDetails", qualifiedByName = "listDetailsId")
    @Mapping(target = "artist", source = "artist", qualifiedByName = "artistId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    FavoriteListDTO toDto(FavoriteList s);

    @Named("listDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ListDetailsDTO toDtoListDetailsId(ListDetails listDetails);

    @Named("artistId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtistDTO toDtoArtistId(Artist artist);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
