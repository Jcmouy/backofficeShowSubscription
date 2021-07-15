package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.EtiquetaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Etiqueta} and its DTO {@link EtiquetaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtiquetaMapper extends EntityMapper<EtiquetaDTO, Etiqueta> {


    @Mapping(target = "obras", ignore = true)
    @Mapping(target = "removeObras", ignore = true)
    Etiqueta toEntity(EtiquetaDTO etiquetaDTO);

    default Etiqueta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setId(id);
        return etiqueta;
    }
}
