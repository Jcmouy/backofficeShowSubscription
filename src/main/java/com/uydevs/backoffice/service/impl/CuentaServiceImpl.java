package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Cuenta;
import com.uydevs.backoffice.dto.domain.CuentaDTO;
import com.uydevs.backoffice.repository.domain.CuentaRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.CuentaService;
import com.uydevs.backoffice.service.mapper.CuentaMapper;

@Service
@Transactional
public class CuentaServiceImpl extends AbstractEntidadService<Cuenta, CuentaDTO> implements CuentaService {

	private final Logger log = LoggerFactory.getLogger(CuentaServiceImpl.class);

	private final CuentaRepository cuentaRepository;

	private final CuentaMapper cuentaMapper;

	public CuentaServiceImpl(CuentaRepository cuentaRepository, CuentaMapper cuentaMapper) {
		this.cuentaRepository = cuentaRepository;
		this.cuentaMapper = cuentaMapper;
	}

	@Override
	public CuentaRepository getEntidadRepository() {
		return cuentaRepository;
	}

	@Override
	public CuentaMapper getEntidadMapper() {
		return cuentaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
