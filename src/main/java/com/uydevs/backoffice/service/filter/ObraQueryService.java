package com.uydevs.backoffice.service.filter;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.uydevs.backoffice.domain.Contenido_;
import com.uydevs.backoffice.domain.Cuenta_;
import com.uydevs.backoffice.domain.Etiqueta_;
import com.uydevs.backoffice.domain.Funcion_;
import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.domain.Obra_;
import com.uydevs.backoffice.domain.TipoDeObra_;
import com.uydevs.backoffice.dto.domain.ObraDTO;
import com.uydevs.backoffice.dto.filter.ObraCriteria;
import com.uydevs.backoffice.repository.domain.ObraRepository;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.mapper.ObraMapper;

@Service
@Transactional(readOnly = true)
public class ObraQueryService extends AbstractQueryService<Obra, ObraDTO, ObraCriteria> {

	private final Logger log = LoggerFactory.getLogger(ObraQueryService.class);

	private final ObraRepository obraRepository;

	private final ObraMapper obraMapper;

	public ObraQueryService(ObraRepository obraRepository, ObraMapper obraMapper) {
		this.obraRepository = obraRepository;
		this.obraMapper = obraMapper;
	}

	@Override
	public ObraRepository getEntidadRepository() {
		return obraRepository;
	}

	@Override
	public ObraMapper getEntidadMapper() {
		return obraMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	protected Specification<Obra> createSpecification(ObraCriteria criteria) {
		Specification<Obra> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), Obra_.id));
			}
			if (criteria.getNombre() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getNombre(), Obra_.nombre));
			}
			if (criteria.getDescripcion() != null && specification != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getDescripcion(), Obra_.descripcion));
			}
			if (criteria.getProtagonistas() != null && specification != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getProtagonistas(), Obra_.protagonistas));
			}
			if (criteria.getDireccion() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getDireccion(), Obra_.direccion));
			}
			if (criteria.getAutores() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getAutores(), Obra_.autores));
			}
			if (criteria.getFecha() != null && specification != null) {
				specification = specification.and(buildRangeSpecification(criteria.getFecha(), Obra_.fecha));
			}
			if (criteria.getDuracion() != null && specification != null) {
				specification = specification.and(buildStringSpecification(criteria.getDuracion(), Obra_.duracion));
			}
			if (criteria.getFuncionesId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getFuncionesId(),
						root -> root.join(Obra_.funciones, JoinType.LEFT).get(Funcion_.id)));
			}
			if (criteria.getContenidosId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getContenidosId(),
						root -> root.join(Obra_.contenidos, JoinType.LEFT).get(Contenido_.id)));
			}
			if (criteria.getEtiquetasId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getEtiquetasId(),
						root -> root.join(Obra_.etiquetas, JoinType.LEFT).get(Etiqueta_.id)));
			}
			if (criteria.getTipoId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getTipoId(),
						root -> root.join(Obra_.tipo, JoinType.LEFT).get(TipoDeObra_.id)));
			}
			if (criteria.getCuentaId() != null && specification != null) {
				specification = specification.and(buildSpecification(criteria.getCuentaId(),
						root -> root.join(Obra_.cuenta, JoinType.LEFT).get(Cuenta_.id)));
			}
		}
		return specification;
	}
}
