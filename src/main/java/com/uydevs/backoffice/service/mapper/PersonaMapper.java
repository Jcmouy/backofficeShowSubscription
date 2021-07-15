package com.uydevs.backoffice.service.mapper;


import com.uydevs.backoffice.domain.*;
import com.uydevs.backoffice.dto.domain.PersonaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Persona} and its DTO {@link PersonaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PaisMapper.class, CuentaMapper.class})
public interface PersonaMapper extends EntityMapper<PersonaDTO, Persona> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "pais.id", target = "paisId")
    @Mapping(source = "cuenta.id", target = "cuentaId")
    @Mapping(source = "cuenta.nombre", target = "cuentaNombre")
    PersonaDTO toDto(Persona persona);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(target = "subscripciones", ignore = true)
    @Mapping(target = "removeSubscripciones", ignore = true)
    @Mapping(source = "paisId", target = "pais")
    @Mapping(source = "cuentaId", target = "cuenta")
    Persona toEntity(PersonaDTO personaDTO);

    default Persona fromId(Long id) {
        if (id == null) {
            return null;
        }
        Persona persona = new Persona();
        persona.setId(id);
        return persona;
    }
}
