package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.ObraDTO;
import com.uydevs.backoffice.dto.filter.ObraCriteria;
import com.uydevs.backoffice.service.domain.ObraService;
import com.uydevs.backoffice.service.filter.ObraQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/obras")
public class ObraResource extends AbstractEntidadController<ObraDTO, ObraCriteria> {
	private final Logger log = LoggerFactory.getLogger(ObraResource.class);

	private static final String ENTITY_NAME = "obra";

	private final ObraService obraService;

	private final ObraQueryService obraQueryService;

	public ObraResource(ObraService obraService, ObraQueryService obraQueryService) {
		this.obraService = obraService;
		this.obraQueryService = obraQueryService;
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
		return "/api/obras";
	}

	@Override
	public ObraService getEntidadService() {
		return obraService;
	}

	@Override
	public ObraQueryService getEntidadQueryService() {
		return obraQueryService;
	}
}
