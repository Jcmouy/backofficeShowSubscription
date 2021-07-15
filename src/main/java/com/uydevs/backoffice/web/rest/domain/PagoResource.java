package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.PagoDTO;
import com.uydevs.backoffice.dto.filter.PagoCriteria;
import com.uydevs.backoffice.service.domain.PagoService;
import com.uydevs.backoffice.service.filter.PagoQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/pagos")
public class PagoResource extends AbstractEntidadController<PagoDTO, PagoCriteria> {
	private final Logger log = LoggerFactory.getLogger(PagoResource.class);

	private static final String ENTITY_NAME = "pago";

	private final PagoService pagoService;

	private final PagoQueryService pagoQueryService;

	public PagoResource(PagoService pagoService, PagoQueryService pagoQueryService) {
		this.pagoService = pagoService;
		this.pagoQueryService = pagoQueryService;
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
		return "/api/pagos";
	}

	@Override
	public PagoService getEntidadService() {
		return pagoService;
	}

	@Override
	public PagoQueryService getEntidadQueryService() {
		return pagoQueryService;
	}
}
