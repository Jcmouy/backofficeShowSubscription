package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Cuenta;
import com.uydevs.backoffice.domain.Cuenta_;
import com.uydevs.backoffice.domain.Obra_;
import com.uydevs.backoffice.domain.Persona_;
import com.uydevs.backoffice.dto.domain.CuentaDTO;
import com.uydevs.backoffice.dto.filter.CuentaCriteria;
import com.uydevs.backoffice.repository.domain.CuentaRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.CuentaMapper;

@Service
@Transactional(readOnly = true)
public class CuentaQueryService extends AbstractQueryService<Cuenta, CuentaDTO, CuentaCriteria> {

	private final Logger log = LoggerFactory.getLogger(CuentaQueryService.class);

	private final CuentaRepository cuentaRepository;

	private final CuentaMapper cuentaMapper;

	public CuentaQueryService(CuentaRepository cuentaRepository, CuentaMapper cuentaMapper) {
		this.cuentaRepository = cuentaRepository;
		this.cuentaMapper = cuentaMapper;
	}

	@Override
	public CuentaRepository getEntidadRepository() {
		return cuentaRepository;
	}

	@Override
	public CuentaMapper getEntidadMapper() {
		return cuentaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Cuenta> createSpecification(CuentaCriteria criteria) {
		Specification<Cuenta> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Cuenta_.id));
			}
			if (criteria.getCodigo() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getCodigo(), Cuenta_.codigo));
			}
			if (criteria.getNombre() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getNombre(), Cuenta_.nombre));
			}
			if (criteria.getFechaBaja() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getFechaBaja(), Cuenta_.fechaBaja));
			}
			if (criteria.getObrasId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getObrasId(),
						root -> root.join(Cuenta_.obras, JoinType.LEFT).get(Obra_.id)));
			}
			if (criteria.getPersonasId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getPersonasId(),
						root -> root.join(Cuenta_.personas, JoinType.LEFT).get(Persona_.id)));
			}
		}
		return specification;
	}
}
