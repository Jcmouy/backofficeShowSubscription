package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Funcion_;
import com.uydevs.backoffice.domain.Pais;
import com.uydevs.backoffice.domain.Pais_;
import com.uydevs.backoffice.domain.Persona_;
import com.uydevs.backoffice.dto.domain.PaisDTO;
import com.uydevs.backoffice.dto.filter.PaisCriteria;
import com.uydevs.backoffice.repository.domain.PaisRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.PaisMapper;

@Service
@Transactional(readOnly = true)
public class PaisQueryService extends AbstractQueryService<Pais, PaisDTO, PaisCriteria> {

	private final Logger log = LoggerFactory.getLogger(PaisQueryService.class);

	private final PaisRepository paisRepository;

	private final PaisMapper paisMapper;

	public PaisQueryService(PaisRepository paisRepository, PaisMapper paisMapper) {
		this.paisRepository = paisRepository;
		this.paisMapper = paisMapper;
	}

	@Override
	public PaisRepository getEntidadRepository() {
		return paisRepository;
	}

	@Override
	public PaisMapper getEntidadMapper() {
		return paisMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Pais> createSpecification(PaisCriteria criteria) {
		Specification<Pais> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Pais_.id));
			}
			if (criteria.getCodigo() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getCodigo(), Pais_.codigo));
			}
			if (criteria.getNombre() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getNombre(), Pais_.nombre));
			}
			if (criteria.getPersonasId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getPersonasId(),
						root -> root.join(Pais_.personas, JoinType.LEFT).get(Persona_.id)));
			}
			if (criteria.getFuncionesId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getFuncionesId(),
						root -> root.join(Pais_.funciones, JoinType.LEFT).get(Funcion_.id)));
			}
		}
		return specification;
	}
}
