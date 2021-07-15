package com.uydevs.backoffice.dto;

import java.io.Serializable;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link com.uydevs.backoffice.domain.entity} entity.
 * This class is used in {@link com.uydevs.backoffice.web.rest.EntityResource}
 * to receive all the possible filtering options from the Http GET request
 * parameters. For example the following could be a valid request:
 * {@code /entities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
public abstract class AbstractEntidadCriteria implements Serializable, Criteria {
	private static final long serialVersionUID = 1L;

	protected LongFilter id;

	public LongFilter getId() {
		return id;
	}

	public void setId(LongFilter id) {
		this.id = id;
	}
}
