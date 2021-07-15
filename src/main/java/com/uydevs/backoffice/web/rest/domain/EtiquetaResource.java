package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.EtiquetaDTO;
import com.uydevs.backoffice.dto.filter.EtiquetaCriteria;
import com.uydevs.backoffice.service.domain.EtiquetaService;
import com.uydevs.backoffice.service.filter.EtiquetaQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/etiquetas")
public class EtiquetaResource extends AbstractEntidadController<EtiquetaDTO, EtiquetaCriteria> {
	private final Logger log = LoggerFactory.getLogger(EtiquetaResource.class);

	private static final String ENTITY_NAME = "etiqueta";

	private final EtiquetaService etiquetaService;

	private final EtiquetaQueryService etiquetaQueryService;

	public EtiquetaResource(EtiquetaService etiquetaService, EtiquetaQueryService etiquetaQueryService) {
		this.etiquetaService = etiquetaService;
		this.etiquetaQueryService = etiquetaQueryService;
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
		return "/api/etiquetas";
	}

	@Override
	public EtiquetaService getEntidadService() {
		return etiquetaService;
	}

	@Override
	public EtiquetaQueryService getEntidadQueryService() {
		return etiquetaQueryService;
	}
}
