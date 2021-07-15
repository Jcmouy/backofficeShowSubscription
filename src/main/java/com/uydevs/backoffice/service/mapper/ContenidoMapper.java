package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.ContenidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contenido} and its DTO {@link ContenidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ObraMapper.class})
public interface ContenidoMapper extends EntityMapper<ContenidoDTO, Contenido> {

    @Mapping(source = "obra.id", target = "obraId")
    @Mapping(source = "obra.nombre", target = "obraNombre")
    ContenidoDTO toDto(Contenido contenido);

    @Mapping(source = "obraId", target = "obra")
    Contenido toEntity(ContenidoDTO contenidoDTO);

    default Contenido fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contenido contenido = new Contenido();
        contenido.setId(id);
        return contenido;
    }
}
