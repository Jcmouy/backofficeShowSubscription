package com.uydevs.backoffice.service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Proceso;
import com.uydevs.backoffice.domain.Proceso_;
import com.uydevs.backoffice.dto.domain.ProcesoDTO;
import com.uydevs.backoffice.dto.filter.ProcesoCriteria;
import com.uydevs.backoffice.repository.domain.ProcesoRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.ProcesoMapper;

@Service
@Transactional(readOnly = true)
public class ProcesoQueryService extends AbstractQueryService<Proceso, ProcesoDTO, ProcesoCriteria> {

	private final Logger log = LoggerFactory.getLogger(ProcesoQueryService.class);

	private final ProcesoRepository procesoRepository;

	private final ProcesoMapper procesoMapper;

	public ProcesoQueryService(ProcesoRepository procesoRepository, ProcesoMapper procesoMapper) {
		this.procesoRepository = procesoRepository;
		this.procesoMapper = procesoMapper;
	}

	@Override
	public ProcesoRepository getEntidadRepository() {
		return procesoRepository;
	}

	@Override
	public ProcesoMapper getEntidadMapper() {
		return procesoMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Proceso> createSpecification(ProcesoCriteria criteria) {
		Specification<Proceso> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Proceso_.id));
			}
			if (criteria.getTipo() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getTipo(), Proceso_.tipo));
			}
			if (criteria.getFecha() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getFecha(), Proceso_.fecha));
			}
		}
		return specification;
	}
}
