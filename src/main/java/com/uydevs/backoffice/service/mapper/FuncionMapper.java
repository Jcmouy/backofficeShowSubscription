package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.FuncionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funcion} and its DTO {@link FuncionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ObraMapper.class, PaisMapper.class, MonedaMapper.class})
public interface FuncionMapper extends EntityMapper<FuncionDTO, Funcion> {

    @Mapping(source = "obra.id", target = "obraId")
    @Mapping(source = "obra.nombre", target = "obraNombre")
    @Mapping(source = "pais.id", target = "paisId")
    @Mapping(source = "pais.nombre", target = "paisNombre")
    @Mapping(source = "moneda.id", target = "monedaId")
    @Mapping(source = "moneda.nombre", target = "monedaNombre")
    FuncionDTO toDto(Funcion funcion);

    @Mapping(target = "subscripciones", ignore = true)
    @Mapping(target = "removeSubscripciones", ignore = true)
    @Mapping(source = "obraId", target = "obra")
    @Mapping(source = "paisId", target = "pais")
    @Mapping(source = "monedaId", target = "moneda")
    Funcion toEntity(FuncionDTO funcionDTO);

    default Funcion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Funcion funcion = new Funcion();
        funcion.setId(id);
        return funcion;
    }
}
