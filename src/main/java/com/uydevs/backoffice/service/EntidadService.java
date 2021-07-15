package com.uydevs.backoffice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

/**
 * Service Interface for managing domain Entity.
 */
public interface EntidadService<T extends AbstractEntidadDto> {

	/**
	 * Save an Entity.
	 *
	 * @param T the entity to save.
	 * @return the persisted entity.
	 */
	T save(T dto);

	/**
	 * Get all the entity.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * Get the "id" entity.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<T> findOne(Long id);

	/**
	 * Delete the "id" entity.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

}
