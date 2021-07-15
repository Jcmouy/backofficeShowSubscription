package com.uydevs.backoffice.web.rest.domain;

import java.net.URISyntaxException;
import java.time.Instant;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.SubscripcionDTO;
import com.uydevs.backoffice.dto.filter.SubscripcionCriteria;
import com.uydevs.backoffice.service.domain.SubscripcionService;
import com.uydevs.backoffice.service.filter.SubscripcionQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/subscripciones")
public class SubscripcionResource extends AbstractEntidadController<SubscripcionDTO, SubscripcionCriteria> {
	private final Logger log = LoggerFactory.getLogger(SubscripcionResource.class);

	private static final String ENTITY_NAME = "subscripcion";

	private final SubscripcionService subscripcionService;

	private final SubscripcionQueryService subscripcionQueryService;

	public SubscripcionResource(SubscripcionService subscripcionService,
			SubscripcionQueryService subscripcionQueryService) {
		this.subscripcionService = subscripcionService;
		this.subscripcionQueryService = subscripcionQueryService;
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
		return "/api/subscripciones";
	}

	@Override
	public SubscripcionService getEntidadService() {
		return subscripcionService;
	}

	@Override
	public SubscripcionQueryService getEntidadQueryService() {
		return subscripcionQueryService;
	}

	@Override
	@PostMapping("")
	public ResponseEntity<SubscripcionDTO> createEntidad(@Valid @RequestBody SubscripcionDTO dto)
			throws URISyntaxException {
		dto.setFecha(Instant.now());
		return super.createEntidad(dto);
	}
}
