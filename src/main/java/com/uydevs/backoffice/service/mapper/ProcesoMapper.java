package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.ProcesoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proceso} and its DTO {@link ProcesoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcesoMapper extends EntityMapper<ProcesoDTO, Proceso> {



    default Proceso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proceso proceso = new Proceso();
        proceso.setId(id);
        return proceso;
    }
}
