package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Proceso;
import com.uydevs.backoffice.dto.domain.ProcesoDTO;
import com.uydevs.backoffice.repository.domain.ProcesoRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.ProcesoService;
import com.uydevs.backoffice.service.mapper.ProcesoMapper;

@Service
@Transactional
public class ProcesoServiceImpl extends AbstractEntidadService<Proceso, ProcesoDTO> implements ProcesoService {

	private final Logger log = LoggerFactory.getLogger(ProcesoServiceImpl.class);

	private final ProcesoRepository procesoRepository;

	private final ProcesoMapper procesoMapper;

	public ProcesoServiceImpl(ProcesoRepository procesoRepository, ProcesoMapper procesoMapper) {
		this.procesoRepository = procesoRepository;
		this.procesoMapper = procesoMapper;
	}

	@Override
	public ProcesoRepository getEntidadRepository() {
		return procesoRepository;
	}

	@Override
	public ProcesoMapper getEntidadMapper() {
		return procesoMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
