package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Cuenta_;
import com.uydevs.backoffice.domain.Pais_;
import com.uydevs.backoffice.domain.Persona;
import com.uydevs.backoffice.domain.Persona_;
import com.uydevs.backoffice.domain.Subscripcion_;
import com.uydevs.backoffice.domain.User_;
import com.uydevs.backoffice.dto.domain.PersonaDTO;
import com.uydevs.backoffice.dto.filter.PersonaCriteria;
import com.uydevs.backoffice.repository.domain.PersonaRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.PersonaMapper;

@Service
@Transactional(readOnly = true)
public class PersonaQueryService extends AbstractQueryService<Persona, PersonaDTO, PersonaCriteria> {

	private final Logger log = LoggerFactory.getLogger(PersonaQueryService.class);

	private final PersonaRepository personaRepository;

	private final PersonaMapper personaMapper;

	public PersonaQueryService(PersonaRepository personaRepository, PersonaMapper personaMapper) {
		this.personaRepository = personaRepository;
		this.personaMapper = personaMapper;
	}

	@Override
	public PersonaRepository getEntidadRepository() {
		return personaRepository;
	}

	@Override
	public PersonaMapper getEntidadMapper() {
		return personaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Persona> createSpecification(PersonaCriteria criteria) {
		Specification<Persona> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Persona_.id));
			}
			if (criteria.getCodigo() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getCodigo(), Persona_.codigo));
			}
			if (criteria.getNombres() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getNombres(), Persona_.nombres));
			}
			if (criteria.getApellidos() != null && specification != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getApellidos(), Persona_.apellidos));
			}
			if (criteria.getFechaNacimiento() != null && specification != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getFechaNacimiento(), Persona_.fechaNacimiento));
			}
			if (criteria.getCorreoElectronico() != null && specification != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCorreoElectronico(), Persona_.correoElectronico));
			}
			if (criteria.getTelefono() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getTelefono(), Persona_.telefono));
			}
			if (criteria.getUsuarioId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getUsuarioId(),
						root -> root.join(Persona_.usuario, JoinType.LEFT).get(User_.id)));
			}
			if (criteria.getSubscripcionesId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getSubscripcionesId(),
						root -> root.join(Persona_.subscripciones, JoinType.LEFT).get(Subscripcion_.id)));
			}
			if (criteria.getPaisId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getPaisId(),
						root -> root.join(Persona_.pais, JoinType.LEFT).get(Pais_.id)));
			}
			if (criteria.getCuentaId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getCuentaId(),
						root -> root.join(Persona_.cuenta, JoinType.LEFT).get(Cuenta_.id)));
			}
		}
		return specification;
	}
}
