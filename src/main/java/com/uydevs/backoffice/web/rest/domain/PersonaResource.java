package com.uydevs.backoffice.web.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uydevs.backoffice.dto.domain.PersonaDTO;
import com.uydevs.backoffice.dto.filter.PersonaCriteria;
import com.uydevs.backoffice.service.domain.PersonaService;
import com.uydevs.backoffice.service.filter.PersonaQueryService;
import com.uydevs.backoffice.web.rest.AbstractEntidadController;

@RestController
@RequestMapping("/api/personas")
public class PersonaResource extends AbstractEntidadController<PersonaDTO, PersonaCriteria> {
	private final Logger log = LoggerFactory.getLogger(PersonaResource.class);

	private static final String ENTITY_NAME = "persona";

	private final PersonaService personaService;

	private final PersonaQueryService personaQueryService;

	public PersonaResource(PersonaService personaService, PersonaQueryService personaQueryService) {
		this.personaService = personaService;
		this.personaQueryService = personaQueryService;
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
		return "/api/personas";
	}

	@Override
	public PersonaService getEntidadService() {
		return personaService;
	}

	@Override
	public PersonaQueryService getEntidadQueryService() {
		return personaQueryService;
	}

}
