package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.PagoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pago} and its DTO {@link PagoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PagoMapper extends EntityMapper<PagoDTO, Pago> {


    @Mapping(target = "subscripcion", ignore = true)
    Pago toEntity(PagoDTO pagoDTO);

    default Pago fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pago pago = new Pago();
        pago.setId(id);
        return pago;
    }
}
