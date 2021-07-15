package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.FuncionDTO;
import com.uydevs.backoffice.dto.filter.FuncionCriteria;
import com.uydevs.backoffice.service.domain.FuncionService;
import com.uydevs.backoffice.service.filter.FuncionQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/funciones")
public class FuncionResource extends AbstractEntidadController<FuncionDTO, FuncionCriteria> {
	private final Logger log = LoggerFactory.getLogger(FuncionResource.class);

	private static final String ENTITY_NAME = "funcion";

	private final FuncionService funcionService;

	private final FuncionQueryService funcionQueryService;

	public FuncionResource(FuncionService funcionService, FuncionQueryService funcionQueryService) {
		this.funcionService = funcionService;
		this.funcionQueryService = funcionQueryService;
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
		return "/api/funciones";
	}

	@Override
	public FuncionService getEntidadService() {
		return funcionService;
	}

	@Override
	public FuncionQueryService getEntidadQueryService() {
		return funcionQueryService;
	}

}
