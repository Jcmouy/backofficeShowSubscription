package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.CuentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuenta} and its DTO {@link CuentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CuentaMapper extends EntityMapper<CuentaDTO, Cuenta> {


    @Mapping(target = "obras", ignore = true)
    @Mapping(target = "removeObras", ignore = true)
    @Mapping(target = "personas", ignore = true)
    @Mapping(target = "removePersonas", ignore = true)
    Cuenta toEntity(CuentaDTO cuentaDTO);

    default Cuenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cuenta cuenta = new Cuenta();
        cuenta.setId(id);
        return cuenta;
    }
}
