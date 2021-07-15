package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Pais;
import com.uydevs.backoffice.dto.domain.PaisDTO;
import com.uydevs.backoffice.repository.domain.PaisRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.PaisService;
import com.uydevs.backoffice.service.mapper.PaisMapper;

@Service
@Transactional
public class PaisServiceImpl extends AbstractEntidadService<Pais, PaisDTO> implements PaisService {

	private final Logger log = LoggerFactory.getLogger(PaisServiceImpl.class);

	private final PaisRepository paisRepository;

	private final PaisMapper paisMapper;

	public PaisServiceImpl(PaisRepository paisRepository, PaisMapper paisMapper) {
		this.paisRepository = paisRepository;
		this.paisMapper = paisMapper;
	}

	@Override
	public PaisRepository getEntidadRepository() {
		return paisRepository;
	}

	@Override
	public PaisMapper getEntidadMapper() {
		return paisMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
