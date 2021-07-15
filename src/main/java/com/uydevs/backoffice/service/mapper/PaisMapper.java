package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.PaisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pais} and its DTO {@link PaisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {


    @Mapping(target = "personas", ignore = true)
    @Mapping(target = "removePersonas", ignore = true)
    @Mapping(target = "funciones", ignore = true)
    @Mapping(target = "removeFunciones", ignore = true)
    Pais toEntity(PaisDTO paisDTO);

    default Pais fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pais pais = new Pais();
        pais.setId(id);
        return pais;
    }
}
