package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.uydevs.backoffice.domain.Funcion_;
import com.uydevs.backoffice.domain.Moneda;
import com.uydevs.backoffice.domain.Moneda_;
import com.uydevs.backoffice.dto.domain.MonedaDTO;
import com.uydevs.backoffice.dto.filter.MonedaCriteria;
import com.uydevs.backoffice.repository.domain.MonedaRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.MonedaMapper;

@Service
@Transactional(readOnly = true)
public class MonedaQueryService extends AbstractQueryService<Moneda, MonedaDTO, MonedaCriteria> {

	private final Logger log = LoggerFactory.getLogger(MonedaQueryService.class);

	private final MonedaRepository monedaRepository;

	private final MonedaMapper monedaMapper;

	public MonedaQueryService(MonedaRepository monedaRepository, MonedaMapper monedaMapper) {
		this.monedaRepository = monedaRepository;
		this.monedaMapper = monedaMapper;
	}

	@Override
	public MonedaRepository getEntidadRepository() {
		return monedaRepository;
	}

	@Override
	public MonedaMapper getEntidadMapper() {
		return monedaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Moneda> createSpecification(MonedaCriteria criteria) {
		Specification<Moneda> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Moneda_.id));
			}
			if (criteria.getCodigo() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getCodigo(), Moneda_.codigo));
			}
			if (criteria.getNombre() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getNombre(), Moneda_.nombre));
			}
			if (criteria.getFuncionesId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getFuncionesId(),
						root -> root.join(Moneda_.funciones, JoinType.LEFT).get(Funcion_.id)));
			}
		}
		return specification;
	}
}
