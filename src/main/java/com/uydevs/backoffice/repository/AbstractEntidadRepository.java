package com.uydevs.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uydevs.backoffice.domain.AbstractEntidad;

public interface AbstractEntidadRepository<T extends AbstractEntidad>
		extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
