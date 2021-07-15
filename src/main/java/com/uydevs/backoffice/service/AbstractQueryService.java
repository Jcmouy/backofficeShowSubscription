package com.uydevs.backoffice.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.AbstractEntidad;
import com.uydevs.backoffice.dto.AbstractEntidadCriteria;
import com.uydevs.backoffice.dto.AbstractEntidadDto;
import com.uydevs.backoffice.repository.AbstractEntidadRepository;
import com.uydevs.backoffice.service.mapper.EntityMapper;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AbstractEntidad} entities in
 * the database. The main input is a {@link EntityCriteria} which gets converted
 * to {@link Specification}, in a way that all the filters must apply. It
 * returns a {@link List} of {@link AbstractEntidadDto} or a {@link Page} of
 * {@link AbstractEntidadDto} which fulfills the criteria.
 */
public abstract class AbstractQueryService<T extends AbstractEntidad, D extends AbstractEntidadDto, F extends AbstractEntidadCriteria>
		extends QueryService<T> {

	public abstract Logger getLogger();

	public abstract AbstractEntidadRepository<T> getEntidadRepository();

	public abstract EntityMapper<D, T> getEntidadMapper();

	/**
	 * Return a {@link List} of {@link AbstractEntidadDto} which matches the
	 * criteria from the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<D> findByCriteria(F criteria) {
		getLogger().debug("find by criteria : {}", criteria);
		final Specification<T> specification = createSpecification(criteria);
		return getEntidadMapper().toDto(getEntidadRepository().findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link AbstractEntidadDto} which matches the
	 * criteria from the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<D> findByCriteria(F criteria, Pageable page) {
		getLogger().debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<T> specification = createSpecification(criteria);
		return getEntidadRepository().findAll(specification, page).map(getEntidadMapper()::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(F criteria) {
		getLogger().debug("count by criteria : {}", criteria);
		final Specification<T> specification = createSpecification(criteria);
		return getEntidadRepository().count(specification);
	}

	/**
	 * Function to convert {@link AbstractEntidadCriteria} to a
	 * {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected abstract Specification<T> createSpecification(F criteria);
}
