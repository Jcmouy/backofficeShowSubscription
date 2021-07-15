package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Contenido;
import com.uydevs.backoffice.dto.domain.ContenidoDTO;
import com.uydevs.backoffice.repository.domain.ContenidoRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.ContenidoService;
import com.uydevs.backoffice.service.mapper.ContenidoMapper;

@Service
@Transactional
public class ContenidoServiceImpl extends AbstractEntidadService<Contenido, ContenidoDTO> implements ContenidoService {

	private final Logger log = LoggerFactory.getLogger(ContenidoServiceImpl.class);

	private final ContenidoRepository contenidoRepository;

	private final ContenidoMapper contenidoMapper;

	public ContenidoServiceImpl(ContenidoRepository contenidoRepository, ContenidoMapper contenidoMapper) {
		this.contenidoRepository = contenidoRepository;
		this.contenidoMapper = contenidoMapper;
	}

	@Override
	public ContenidoRepository getEntidadRepository() {
		return contenidoRepository;
	}

	@Override
	public ContenidoMapper getEntidadMapper() {
		return contenidoMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
