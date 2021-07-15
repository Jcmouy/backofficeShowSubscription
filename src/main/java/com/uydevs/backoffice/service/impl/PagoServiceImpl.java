package com.uydevs.backoffice.service.impl;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.Pago;
import com.uydevs.backoffice.dto.domain.PagoDTO;
import com.uydevs.backoffice.repository.domain.PagoRepository;
import com.uydevs.backoffice.service.AbstractEntidadService;
import com.uydevs.backoffice.service.domain.PagoService;
import com.uydevs.backoffice.service.mapper.PagoMapper;

@Service
@Transactional
public class PagoServiceImpl extends AbstractEntidadService<Pago, PagoDTO> implements PagoService {

	private final Logger log = LoggerFactory.getLogger(PagoServiceImpl.class);

	private final PagoRepository pagoRepository;

	private final PagoMapper pagoMapper;

	public PagoServiceImpl(PagoRepository pagoRepository, PagoMapper pagoMapper) {
		this.pagoRepository = pagoRepository;
		this.pagoMapper = pagoMapper;
	}

	@Override
	public PagoRepository getEntidadRepository() {
		return pagoRepository;
	}

	@Override
	public PagoMapper getEntidadMapper() {
		return pagoMapper;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public PagoDTO save(PagoDTO dto) {
		if (dto.getFechaExterna() == null) {
			dto.setFechaExterna(Instant.now());
		}
		return super.save(dto);
	}

	/**
	 * Get all the pagos where Subscripcion is {@code null}.
	 * 
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PagoDTO> findAllWhereSubscripcionIsNull() {
		log.debug("Request to get all pagos where Subscripcion is null");
		return StreamSupport.stream(pagoRepository.findAll().spliterator(), false)
				.filter(pago -> pago.getSubscripcion() == null).map(pagoMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}
}
