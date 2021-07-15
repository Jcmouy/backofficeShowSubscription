package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Pago;
import com.uydevs.backoffice.domain.Pago_;
import com.uydevs.backoffice.domain.Subscripcion_;
import com.uydevs.backoffice.dto.domain.PagoDTO;
import com.uydevs.backoffice.dto.filter.PagoCriteria;
import com.uydevs.backoffice.repository.domain.PagoRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.PagoMapper;

@Service
@Transactional(readOnly = true)
public class PagoQueryService extends AbstractQueryService<Pago, PagoDTO, PagoCriteria> {

	private final Logger log = LoggerFactory.getLogger(PagoQueryService.class);

	private final PagoRepository pagoRepository;

	private final PagoMapper pagoMapper;

	public PagoQueryService(PagoRepository pagoRepository, PagoMapper pagoMapper) {
		this.pagoRepository = pagoRepository;
		this.pagoMapper = pagoMapper;
	}

	@Override
	public PagoRepository getEntidadRepository() {
		return pagoRepository;
	}

	@Override
	public PagoMapper getEntidadMapper() {
		return pagoMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Pago> createSpecification(PagoCriteria criteria) {
		Specification<Pago> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Pago_.id));
			}
			if (criteria.getIdExterno() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getIdExterno(), Pago_.idExterno));
			}
			if (criteria.getFechaExterna() != null && specification != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getFechaExterna(), Pago_.fechaExterna));
			}
			if (criteria.getSubscripcionId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getSubscripcionId(),
						root -> root.join(Pago_.subscripcion, JoinType.LEFT).get(Subscripcion_.id)));
			}
		}
		return specification;
	}
}
