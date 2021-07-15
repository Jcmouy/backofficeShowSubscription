package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.uydevs.backoffice.domain.Etiqueta;
import com.uydevs.backoffice.domain.Etiqueta_;
import com.uydevs.backoffice.domain.Obra_;
import com.uydevs.backoffice.dto.domain.EtiquetaDTO;
import com.uydevs.backoffice.dto.filter.EtiquetaCriteria;
import com.uydevs.backoffice.repository.domain.EtiquetaRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.EtiquetaMapper;

@Service
@Transactional(readOnly = true)
public class EtiquetaQueryService extends AbstractQueryService<Etiqueta, EtiquetaDTO, EtiquetaCriteria> {

	private final Logger log = LoggerFactory.getLogger(EtiquetaQueryService.class);

	private final EtiquetaRepository etiquetaRepository;

	private final EtiquetaMapper etiquetaMapper;

	public EtiquetaQueryService(EtiquetaRepository etiquetaRepository, EtiquetaMapper etiquetaMapper) {
		this.etiquetaRepository = etiquetaRepository;
		this.etiquetaMapper = etiquetaMapper;
	}

	@Override
	public EtiquetaRepository getEntidadRepository() {
		return etiquetaRepository;
	}

	@Override
	public EtiquetaMapper getEntidadMapper() {
		return etiquetaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Etiqueta> createSpecification(EtiquetaCriteria criteria) {
		Specification<Etiqueta> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Etiqueta_.id));
			}
			if (criteria.getNombre() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getNombre(), Etiqueta_.nombre));
			}
			if (criteria.getObrasId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getObrasId(),
						root -> root.join(Etiqueta_.obras, JoinType.LEFT).get(Obra_.id)));
			}
		}
		return specification;
	}
}
