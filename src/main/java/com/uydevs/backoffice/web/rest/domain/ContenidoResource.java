package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.ContenidoDTO;
import com.uydevs.backoffice.dto.filter.ContenidoCriteria;
import com.uydevs.backoffice.service.domain.ContenidoService;
import com.uydevs.backoffice.service.filter.ContenidoQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/contenidos")
public class ContenidoResource extends AbstractEntidadController<ContenidoDTO, ContenidoCriteria> {

	private final Logger log = LoggerFactory.getLogger(ContenidoResource.class);

	private static final String ENTITY_NAME = "contenido";

	private final ContenidoService contenidoService;

	private final ContenidoQueryService contenidoQueryService;

	public ContenidoResource(ContenidoService contenidoService, ContenidoQueryService contenidoQueryService) {
		this.contenidoService = contenidoService;
		this.contenidoQueryService = contenidoQueryService;
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
		return "/api/contenidos";
	}

	@Override
	public ContenidoService getEntidadService() {
		return contenidoService;
	}

	@Override
	public ContenidoQueryService getEntidadQueryService() {
		return contenidoQueryService;
	}
}
