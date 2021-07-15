package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.CuentaDTO;
import com.uydevs.backoffice.dto.filter.CuentaCriteria;
import com.uydevs.backoffice.service.domain.CuentaService;
import com.uydevs.backoffice.service.filter.CuentaQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaResource extends AbstractEntidadController<CuentaDTO, CuentaCriteria> {
	private final Logger log = LoggerFactory.getLogger(CuentaResource.class);

	private static final String ENTITY_NAME = "cuenta";

	private final CuentaService cuentaService;

	private final CuentaQueryService cuentaQueryService;

	public CuentaResource(CuentaService cuentaService, CuentaQueryService cuentaQueryService) {
		this.cuentaService = cuentaService;
		this.cuentaQueryService = cuentaQueryService;
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
		return "/api/cuentas";
	}

	@Override
	public CuentaService getEntidadService() {
		return cuentaService;
	}

	@Override
	public CuentaQueryService getEntidadQueryService() {
		return cuentaQueryService;
	}
}
