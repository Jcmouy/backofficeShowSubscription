package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Moneda;
import com.uydevs.backoffice.dto.domain.MonedaDTO;
import com.uydevs.backoffice.repository.domain.MonedaRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.MonedaService;
import com.uydevs.backoffice.service.mapper.MonedaMapper;

@Service
@Transactional
public class MonedaServiceImpl extends AbstractEntidadService<Moneda, MonedaDTO> implements MonedaService {

	private final Logger log = LoggerFactory.getLogger(MonedaServiceImpl.class);

	private final MonedaRepository monedaRepository;

	private final MonedaMapper monedaMapper;

	public MonedaServiceImpl(MonedaRepository monedaRepository, MonedaMapper monedaMapper) {
		this.monedaRepository = monedaRepository;
		this.monedaMapper = monedaMapper;
	}

	@Override
	public MonedaRepository getEntidadRepository() {
		return monedaRepository;
	}

	@Override
	public MonedaMapper getEntidadMapper() {
		return monedaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
