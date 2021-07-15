package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Funcion_;
import com.uydevs.backoffice.domain.Pago_;
import com.uydevs.backoffice.domain.Persona_;
import com.uydevs.backoffice.domain.Subscripcion;
import com.uydevs.backoffice.domain.Subscripcion_;
import com.uydevs.backoffice.dto.domain.SubscripcionDTO;
import com.uydevs.backoffice.dto.filter.SubscripcionCriteria;
import com.uydevs.backoffice.repository.domain.SubscripcionRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.SubscripcionMapper;

@Service
@Transactional(readOnly = true)
public class SubscripcionQueryService
		extends AbstractQueryService<Subscripcion, SubscripcionDTO, SubscripcionCriteria> {

	private final Logger log = LoggerFactory.getLogger(SubscripcionQueryService.class);

	private final SubscripcionRepository subscripcionRepository;

	private final SubscripcionMapper subscripcionMapper;

	public SubscripcionQueryService(SubscripcionRepository subscripcionRepository,
			SubscripcionMapper subscripcionMapper) {
		this.subscripcionRepository = subscripcionRepository;
		this.subscripcionMapper = subscripcionMapper;
	}

	@Override
	public SubscripcionRepository getEntidadRepository() {
		return subscripcionRepository;
	}

	@Override
	public SubscripcionMapper getEntidadMapper() {
		return subscripcionMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Subscripcion> createSpecification(SubscripcionCriteria criteria) {
		Specification<Subscripcion> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Subscripcion_.id));
			}
			if (criteria.getFecha() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getFecha(), Subscripcion_.fecha));
			}
			if (criteria.getMetodoPago() != null && specification != null) {
				specification = specification
						.and(buildSpecification(criteria.getMetodoPago(), Subscripcion_.metodoPago));
			}
			if (criteria.getPagoId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getPagoId(),
						root -> root.join(Subscripcion_.pago, JoinType.LEFT).get(Pago_.id)));
			}
			if (criteria.getFuncionId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getFuncionId(),
						root -> root.join(Subscripcion_.funcion, JoinType.LEFT).get(Funcion_.id)));
			}
			if (criteria.getPersonaId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getPersonaId(),
						root -> root.join(Subscripcion_.persona, JoinType.LEFT).get(Persona_.id)));
			}
		}
		return specification;
	}
}
