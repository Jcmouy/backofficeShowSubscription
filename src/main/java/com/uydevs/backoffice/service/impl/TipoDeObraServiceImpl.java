package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.TipoDeObra;
import com.uydevs.backoffice.dto.domain.TipoDeObraDTO;
import com.uydevs.backoffice.repository.domain.TipoDeObraRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.TipoDeObraService;
import com.uydevs.backoffice.service.mapper.TipoDeObraMapper;

@Service
@Transactional
public class TipoDeObraServiceImpl extends AbstractEntidadService<TipoDeObra, TipoDeObraDTO>
		implements TipoDeObraService {

	private final Logger log = LoggerFactory.getLogger(TipoDeObraServiceImpl.class);

	private final TipoDeObraRepository tipoDeObraRepository;

	private final TipoDeObraMapper tipoDeObraMapper;

	public TipoDeObraServiceImpl(TipoDeObraRepository tipoDeObraRepository, TipoDeObraMapper tipoDeObraMapper) {
		this.tipoDeObraRepository = tipoDeObraRepository;
		this.tipoDeObraMapper = tipoDeObraMapper;
	}

	@Override
	public TipoDeObraRepository getEntidadRepository() {
		return tipoDeObraRepository;
	}

	@Override
	public TipoDeObraMapper getEntidadMapper() {
		return tipoDeObraMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
