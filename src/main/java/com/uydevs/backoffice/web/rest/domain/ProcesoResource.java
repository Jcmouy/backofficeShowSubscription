package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.ProcesoDTO;
import com.uydevs.backoffice.dto.filter.ProcesoCriteria;
import com.uydevs.backoffice.service.domain.ProcesoService;
import com.uydevs.backoffice.service.filter.ProcesoQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/procesos")
public class ProcesoResource extends AbstractEntidadController<ProcesoDTO, ProcesoCriteria> {
	private final Logger log = LoggerFactory.getLogger(ProcesoResource.class);

	private static final String ENTITY_NAME = "proceso";

	private final ProcesoService procesoService;

	private final ProcesoQueryService procesoQueryService;

	public ProcesoResource(ProcesoService procesoService, ProcesoQueryService procesoQueryService) {
		this.procesoService = procesoService;
		this.procesoQueryService = procesoQueryService;
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
		return "/api/procesos";
	}

	@Override
	public ProcesoService getEntidadService() {
		return procesoService;
	}

	@Override
	public ProcesoQueryService getEntidadQueryService() {
		return procesoQueryService;
	}
}
