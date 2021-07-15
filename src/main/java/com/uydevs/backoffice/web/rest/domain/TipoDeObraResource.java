package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.TipoDeObraDTO;
import com.uydevs.backoffice.dto.filter.TipoDeObraCriteria;
import com.uydevs.backoffice.service.domain.TipoDeObraService;
import com.uydevs.backoffice.service.filter.TipoDeObraQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/tipo-de-obras")
public class TipoDeObraResource extends AbstractEntidadController<TipoDeObraDTO, TipoDeObraCriteria> {
	private final Logger log = LoggerFactory.getLogger(TipoDeObraResource.class);

	private static final String ENTITY_NAME = "tipoDeObra";

	private final TipoDeObraService tipoDeObraService;

	private final TipoDeObraQueryService tipoDeObraQueryService;

	public TipoDeObraResource(TipoDeObraService tipoDeObraService, TipoDeObraQueryService tipoDeObraQueryService) {
		this.tipoDeObraService = tipoDeObraService;
		this.tipoDeObraQueryService = tipoDeObraQueryService;
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
		return "/api//tipo-de-obras";
	}

	@Override
	public TipoDeObraService getEntidadService() {
		return tipoDeObraService;
	}

	@Override
	public TipoDeObraQueryService getEntidadQueryService() {
		return tipoDeObraQueryService;
	}
}
