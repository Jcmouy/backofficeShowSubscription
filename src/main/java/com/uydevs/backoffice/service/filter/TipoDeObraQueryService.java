package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Obra_;
import com.uydevs.backoffice.domain.TipoDeObra;
import com.uydevs.backoffice.domain.TipoDeObra_;
import com.uydevs.backoffice.dto.domain.TipoDeObraDTO;
import com.uydevs.backoffice.dto.filter.TipoDeObraCriteria;
import com.uydevs.backoffice.repository.domain.TipoDeObraRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.TipoDeObraMapper;

@Service
@Transactional(readOnly = true)
public class TipoDeObraQueryService extends AbstractQueryService<TipoDeObra, TipoDeObraDTO, TipoDeObraCriteria> {

	private final Logger log = LoggerFactory.getLogger(TipoDeObraQueryService.class);

	private final TipoDeObraRepository tipoDeObraRepository;

	private final TipoDeObraMapper tipoDeObraMapper;

	public TipoDeObraQueryService(TipoDeObraRepository tipoDeObraRepository, TipoDeObraMapper tipoDeObraMapper) {
		this.tipoDeObraRepository = tipoDeObraRepository;
		this.tipoDeObraMapper = tipoDeObraMapper;
	}

	@Override
	public TipoDeObraRepository getEntidadRepository() {
		return tipoDeObraRepository;
	}

	@Override
	public TipoDeObraMapper getEntidadMapper() {
		return tipoDeObraMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<TipoDeObra> createSpecification(TipoDeObraCriteria criteria) {
		Specification<TipoDeObra> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), TipoDeObra_.id));
			}
			if (criteria.getTipo() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getTipo(), TipoDeObra_.tipo));
			}
			if (criteria.getDescripcion() != null && specification != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getDescripcion(), TipoDeObra_.descripcion));
			}
			if (criteria.getObrasId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getObrasId(),
						root -> root.join(TipoDeObra_.obras, JoinType.LEFT).get(Obra_.id)));
			}
		}
		return specification;
	}
}
