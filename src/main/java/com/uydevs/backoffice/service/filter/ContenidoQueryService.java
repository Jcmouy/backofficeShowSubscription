package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.uydevs.backoffice.domain.Contenido;
import com.uydevs.backoffice.domain.Contenido_;
import com.uydevs.backoffice.domain.Obra_;
import com.uydevs.backoffice.dto.domain.ContenidoDTO;
import com.uydevs.backoffice.dto.filter.ContenidoCriteria;
import com.uydevs.backoffice.repository.domain.ContenidoRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.ContenidoMapper;

@Service
@Transactional(readOnly = true)
public class ContenidoQueryService extends AbstractQueryService<Contenido, ContenidoDTO, ContenidoCriteria> {

	private final Logger log = LoggerFactory.getLogger(ContenidoQueryService.class);

	private final ContenidoRepository contenidoRepository;

	private final ContenidoMapper contenidoMapper;

	public ContenidoQueryService(ContenidoRepository contenidoRepository, ContenidoMapper contenidoMapper) {
		this.contenidoRepository = contenidoRepository;
		this.contenidoMapper = contenidoMapper;
	}

	@Override
	public ContenidoRepository getEntidadRepository() {
		return contenidoRepository;
	}

	@Override
	public ContenidoMapper getEntidadMapper() {
		return contenidoMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Contenido> createSpecification(ContenidoCriteria criteria) {
		Specification<Contenido> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Contenido_.id));
			}
			if (criteria.getIndice() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getIndice(), Contenido_.indice));
			}
			if (criteria.getSubindice() != null && specification != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getSubindice(), Contenido_.subindice));
			}
			if (criteria.getTipoContenido() != null && specification != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getTipoContenido(), Contenido_.tipoContenido));
			}
			if (criteria.getValor() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getValor(), Contenido_.valor));
			}
			if (criteria.getResumen() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getResumen(), Contenido_.resumen));
			}
			if (criteria.getObraId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getObraId(),
						root -> root.join(Contenido_.obra, JoinType.LEFT).get(Obra_.id)));
			}
		}
		return specification;
	}
}
