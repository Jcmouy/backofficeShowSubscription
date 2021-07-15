package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Persona;
import com.uydevs.backoffice.dto.domain.PersonaDTO;
import com.uydevs.backoffice.repository.domain.PersonaRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.PersonaService;
import com.uydevs.backoffice.service.mapper.PersonaMapper;

@Service
@Transactional
public class PersonaServiceImpl extends AbstractEntidadService<Persona, PersonaDTO> implements PersonaService {

	private final Logger log = LoggerFactory.getLogger(PersonaServiceImpl.class);

	private final PersonaRepository personaRepository;

	private final PersonaMapper personaMapper;

	public PersonaServiceImpl(PersonaRepository personaRepository, PersonaMapper personaMapper) {
		this.personaRepository = personaRepository;
		this.personaMapper = personaMapper;
	}

	@Override
	public PersonaRepository getEntidadRepository() {
		return personaRepository;
	}

	@Override
	public PersonaMapper getEntidadMapper() {
		return personaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
