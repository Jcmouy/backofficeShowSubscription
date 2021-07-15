package com.uydevs.backoffice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Etiqueta;
import com.uydevs.backoffice.dto.domain.EtiquetaDTO;
import com.uydevs.backoffice.repository.domain.EtiquetaRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.EtiquetaService;
import com.uydevs.backoffice.service.mapper.EtiquetaMapper;

@Service
@Transactional
public class EtiquetaServiceImpl extends AbstractEntidadService<Etiqueta, EtiquetaDTO> implements EtiquetaService {

	private final Logger log = LoggerFactory.getLogger(EtiquetaServiceImpl.class);

	private final EtiquetaRepository etiquetaRepository;

	private final EtiquetaMapper etiquetaMapper;

	public EtiquetaServiceImpl(EtiquetaRepository etiquetaRepository, EtiquetaMapper etiquetaMapper) {
		this.etiquetaRepository = etiquetaRepository;
		this.etiquetaMapper = etiquetaMapper;
	}

	@Override
	public EtiquetaRepository getEntidadRepository() {
		return etiquetaRepository;
	}

	@Override
	public EtiquetaMapper getEntidadMapper() {
		return etiquetaMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
}
