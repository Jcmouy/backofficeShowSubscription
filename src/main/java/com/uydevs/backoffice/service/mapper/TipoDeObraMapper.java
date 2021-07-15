package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.TipoDeObraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDeObra} and its DTO {@link TipoDeObraDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeObraMapper extends EntityMapper<TipoDeObraDTO, TipoDeObra> {


    @Mapping(target = "obras", ignore = true)
    @Mapping(target = "removeObras", ignore = true)
    TipoDeObra toEntity(TipoDeObraDTO tipoDeObraDTO);

    default TipoDeObra fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeObra tipoDeObra = new TipoDeObra();
        tipoDeObra.setId(id);
        return tipoDeObra;
    }
}
