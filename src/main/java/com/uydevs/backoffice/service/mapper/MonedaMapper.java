package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.MonedaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Moneda} and its DTO {@link MonedaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MonedaMapper extends EntityMapper<MonedaDTO, Moneda> {


    @Mapping(target = "funciones", ignore = true)
    @Mapping(target = "removeFunciones", ignore = true)
    Moneda toEntity(MonedaDTO monedaDTO);

    default Moneda fromId(Long id) {
        if (id == null) {
            return null;
        }
        Moneda moneda = new Moneda();
        moneda.setId(id);
        return moneda;
    }
}
