package com.uydevs.backoffice.service.domain;

import java.util.List;

import com.uydevs.backoffice.dto.domain.PagoDTO;
import com.uydevs.backoffice.service.EntidadService;

public interface PagoService extends EntidadService<PagoDTO> {
	/**
	 * Get all the PagoDTO where Subscripcion is {@code null}.
	 *
	 * @return the {@link List} of entities.
	 */
	List<PagoDTO> findAllWhereSubscripcionIsNull();
}
