package com.uydevs.backoffice.service.impl;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Subscripcion;
import com.uydevs.backoffice.dto.domain.SubscripcionDTO;
import com.uydevs.backoffice.repository.domain.SubscripcionRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.SubscripcionService;
import com.uydevs.backoffice.service.mapper.SubscripcionMapper;

@Service
@Transactional
public class SubscripcionServiceImpl extends AbstractEntidadService<Subscripcion, SubscripcionDTO>
		implements SubscripcionService {

	private final Logger log = LoggerFactory.getLogger(SubscripcionServiceImpl.class);

	private final SubscripcionRepository subscripcionRepository;

	private final SubscripcionMapper subscripcionMapper;

	public SubscripcionServiceImpl(SubscripcionRepository subscripcionRepository,
			SubscripcionMapper subscripcionMapper) {
		this.subscripcionRepository = subscripcionRepository;
		this.subscripcionMapper = subscripcionMapper;
	}

	@Override
	public SubscripcionRepository getEntidadRepository() {
		return subscripcionRepository;
	}

	@Override
	public SubscripcionMapper getEntidadMapper() {
		return subscripcionMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public SubscripcionDTO save(SubscripcionDTO dto) {
		if (dto.getFecha() == null) {
			dto.setFecha(Instant.now());
		}
		return super.save(dto);
	}
}
