package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.uydevs.backoffice.domain.Funcion;
import com.uydevs.backoffice.domain.Funcion_;
import com.uydevs.backoffice.domain.Moneda_;
import com.uydevs.backoffice.domain.Obra_;
import com.uydevs.backoffice.domain.Pais_;
import com.uydevs.backoffice.domain.Subscripcion_;
import com.uydevs.backoffice.dto.domain.FuncionDTO;
import com.uydevs.backoffice.dto.filter.FuncionCriteria;
import com.uydevs.backoffice.repository.domain.FuncionRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.FuncionMapper;

@Service
@Transactional(readOnly = true)
public class FuncionQueryService extends AbstractQueryService<Funcion, FuncionDTO, FuncionCriteria> {

	private final Logger log = LoggerFactory.getLogger(FuncionQueryService.class);

	private final FuncionRepository funcionRepository;

	private final FuncionMapper funcionMapper;

	public FuncionQueryService(FuncionRepository funcionRepository, FuncionMapper funcionMapper) {
		this.funcionRepository = funcionRepository;
		this.funcionMapper = funcionMapper;
	}

	@Override
	public FuncionRepository getEntidadRepository() {
		return funcionRepository;
	}

	@Override
	public FuncionMapper getEntidadMapper() {
		return funcionMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Funcion> createSpecification(FuncionCriteria criteria) {
		Specification<Funcion> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Funcion_.id));
			}
			if (criteria.getFecha() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getFecha(), Funcion_.fecha));
			}
			if (criteria.getPrecio() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getPrecio(), Funcion_.precio));
			}
			if (criteria.getSubscripcionesId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getSubscripcionesId(),
						root -> root.join(Funcion_.subscripciones, JoinType.LEFT).get(Subscripcion_.id)));
			}
			if (criteria.getObraId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getObraId(),
						root -> root.join(Funcion_.obra, JoinType.LEFT).get(Obra_.id)));
			}
			if (criteria.getPaisId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getPaisId(),
						root -> root.join(Funcion_.pais, JoinType.LEFT).get(Pais_.id)));
			}
			if (criteria.getMonedaId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getMonedaId(),
						root -> root.join(Funcion_.moneda, JoinType.LEFT).get(Moneda_.id)));
			}
		}
		return specification;
	}

//	@Override
//	@Transactional(readOnly = true)
//	public Page<FuncionDTO> findByCriteria(FuncionCriteria criteria, Pageable page) {
//		Page<FuncionDTO> funciones = super.findByCriteria(criteria, page);
//		
//		return funciones;
//	}
}
