package com.uydevs.backoffice.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.domain.AbstractEntidad;
import com.uydevs.backoffice.dto.AbstractEntidadDto;
import com.uydevs.backoffice.repository.AbstractEntidadRepository;
import com.uydevs.backoffice.service.mapper.EntityMapper;

public abstract class AbstractEntidadService<T extends AbstractEntidad, D extends AbstractEntidadDto>
		implements EntidadService<D> {

	public abstract Logger getLogger();

	public abstract AbstractEntidadRepository<T> getEntidadRepository();

	public abstract EntityMapper<D, T> getEntidadMapper();

	@Override
	public D save(D dto) {
		getLogger().debug("Request to save Entity : {}", dto);
		T entidad = getEntidadMapper().toEntity(dto);
		entidad = getEntidadRepository().save(entidad);
		return getEntidadMapper().toDto(entidad);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<D> findAll(Pageable pageable) {
		getLogger().debug("Request to get all Entities");
		return getEntidadRepository().findAll(pageable).map(getEntidadMapper()::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<D> findOne(Long id) {
		getLogger().debug("Request to get Entity : {}", id);
		return getEntidadRepository().findById(id).map(getEntidadMapper()::toDto);
	}

	@Override
	public void delete(Long id) {
		getLogger().debug("Request to delete Entity : {}", id);
		getEntidadRepository().deleteById(id);
	}
}
