package com.uydevs.backoffice.repository.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uydevs.backoffice.domain.Subscripcion;
import com.uydevs.backoffice.repository.AbstractEntidadRepository;

@Repository
public interface SubscripcionRepository extends AbstractEntidadRepository<Subscripcion> {

	@Query(value = "select distinct subscripcion from Subscripcion subscripcion left join fetch subscripcion.funcion", countQuery = "select count(distinct subscripcion) from Subscripcion subscripcion")
	Page<Subscripcion> findAllWithEagerRelationships(Pageable pageable);

}
