package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.MonedaDTO;
import com.uydevs.backoffice.dto.filter.MonedaCriteria;
import com.uydevs.backoffice.service.domain.MonedaService;
import com.uydevs.backoffice.service.filter.MonedaQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/monedas")
public class MonedaResource extends AbstractEntidadController<MonedaDTO, MonedaCriteria> {
	private final Logger log = LoggerFactory.getLogger(MonedaResource.class);

	private static final String ENTITY_NAME = "moneda";

	private final MonedaService monedaService;

	private final MonedaQueryService monedaQueryService;

	public MonedaResource(MonedaService monedaService, MonedaQueryService monedaQueryService) {
		this.monedaService = monedaService;
		this.monedaQueryService = monedaQueryService;
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
		return "/api/monedas";
	}

	@Override
	public MonedaService getEntidadService() {
		return monedaService;
	}

	@Override
	public MonedaQueryService getEntidadQueryService() {
		return monedaQueryService;
	}
}
