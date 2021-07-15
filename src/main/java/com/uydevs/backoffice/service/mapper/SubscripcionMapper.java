package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.SubscripcionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subscripcion} and its DTO {@link SubscripcionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PagoMapper.class, FuncionMapper.class, PersonaMapper.class})
public interface SubscripcionMapper extends EntityMapper<SubscripcionDTO, Subscripcion> {

    @Mapping(source = "pago.id", target = "pagoId")
    @Mapping(source = "funcion.id", target = "funcionId")
    @Mapping(source = "persona.id", target = "personaId")
    SubscripcionDTO toDto(Subscripcion subscripcion);

    @Mapping(source = "pagoId", target = "pago")
    @Mapping(source = "funcionId", target = "funcion")
    @Mapping(source = "personaId", target = "persona")
    Subscripcion toEntity(SubscripcionDTO subscripcionDTO);

    default Subscripcion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subscripcion subscripcion = new Subscripcion();
        subscripcion.setId(id);
        return subscripcion;
    }
}
