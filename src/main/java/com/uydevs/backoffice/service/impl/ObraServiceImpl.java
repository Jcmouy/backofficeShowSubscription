package com.uydevs.backoffice.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.dto.domain.ObraDTO;
import com.uydevs.backoffice.repository.domain.ObraRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.ObraService;
import com.uydevs.backoffice.service.mapper.ObraMapper;

@Service
@Transactional
public class ObraServiceImpl extends AbstractEntidadService<Obra, ObraDTO> implements ObraService {

	private final Logger log = LoggerFactory.getLogger(ObraServiceImpl.class);

	private final ObraRepository obraRepository;

	private final ObraMapper obraMapper;

	public ObraServiceImpl(ObraRepository obraRepository, ObraMapper obraMapper) {
		this.obraRepository = obraRepository;
		this.obraMapper = obraMapper;
	}

	@Override
	public ObraRepository getEntidadRepository() {
		return obraRepository;
	}

	@Override
	public ObraMapper getEntidadMapper() {
		return obraMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public Page<ObraDTO> findAllWithEagerRelationships(Pageable pageable) {
		return obraRepository.findAllWithEagerRelationships(pageable).map(obraMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ObraDTO> findOne(Long id) {
		getLogger().debug("Request to get Entity : {}", id);
		Optional<Obra> obra = getEntidadRepository().findOneWithEagerRelationships(id);
		return obra.map(getEntidadMapper()::toDto);
	}
}
