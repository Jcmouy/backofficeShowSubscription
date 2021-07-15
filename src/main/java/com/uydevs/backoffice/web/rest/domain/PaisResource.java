package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.PaisDTO;
import com.uydevs.backoffice.dto.filter.PaisCriteria;
import com.uydevs.backoffice.service.domain.PaisService;
import com.uydevs.backoffice.service.filter.PaisQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/paises")
public class PaisResource extends AbstractEntidadController<PaisDTO, PaisCriteria> {
	private final Logger log = LoggerFactory.getLogger(PaisResource.class);

	private static final String ENTITY_NAME = "pais";

	private final PaisService paisService;

	private final PaisQueryService paisQueryService;

	public PaisResource(PaisService paisService, PaisQueryService paisQueryService) {
		this.paisService = paisService;
		this.paisQueryService = paisQueryService;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public String getApiUrl() {
		return "/api/paises";
	}

	@Override
	public PaisService getEntidadService() {
		return paisService;
	}

	@Override
	public PaisQueryService getEntidadQueryService() {
		return paisQueryService;
	}
}
