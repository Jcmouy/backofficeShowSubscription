package com.uydevs.backoffice.service.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uydevs.backoffice.dto.domain.ObraDTO;
import com.uydevs.backoffice.service.EntidadService;

public interface ObraService extends EntidadService<ObraDTO> {

	/**
	 * Get all the obras with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	Page<ObraDTO> findAllWithEagerRelationships(Pageable pageable);
}
