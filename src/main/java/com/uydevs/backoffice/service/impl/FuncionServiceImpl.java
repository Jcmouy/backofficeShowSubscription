package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Funcion;
import com.uydevs.backoffice.dto.domain.FuncionDTO;
import com.uydevs.backoffice.repository.domain.FuncionRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.FuncionService;
import com.uydevs.backoffice.service.mapper.FuncionMapper;

@Service
@Transactional
public class FuncionServiceImpl extends AbstractEntidadService<Funcion, FuncionDTO> implements FuncionService {

	private final Logger log = LoggerFactory.getLogger(FuncionServiceImpl.class);

	private final FuncionRepository funcionRepository;

	private final FuncionMapper funcionMapper;

	public FuncionServiceImpl(FuncionRepository funcionRepository, FuncionMapper funcionMapper) {
		this.funcionRepository = funcionRepository;
		this.funcionMapper = funcionMapper;
	}

	@Override
	public FuncionRepository getEntidadRepository() {
		return funcionRepository;
	}

	@Override
	public FuncionMapper getEntidadMapper() {
		return funcionMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
