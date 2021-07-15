package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.ObraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Obra} and its DTO {@link ObraDTO}.
 */
@Mapper(componentModel = "spring", uses = {EtiquetaMapper.class, TipoDeObraMapper.class, CuentaMapper.class})
public interface ObraMapper extends EntityMapper<ObraDTO, Obra> {

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.tipo", target = "tipoTipo")
    @Mapping(source = "cuenta.id", target = "cuentaId")
    @Mapping(source = "cuenta.nombre", target = "cuentaNombre")
    ObraDTO toDto(Obra obra);

    @Mapping(target = "funciones", ignore = true)
    @Mapping(target = "removeFunciones", ignore = true)
    @Mapping(target = "contenidos", ignore = true)
    @Mapping(target = "removeContenidos", ignore = true)
    @Mapping(target = "removeEtiquetas", ignore = true)
    @Mapping(source = "tipoId", target = "tipo")
    @Mapping(source = "cuentaId", target = "cuenta")
    Obra toEntity(ObraDTO obraDTO);

    default Obra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Obra obra = new Obra();
        obra.setId(id);
        return obra;
    }
}
