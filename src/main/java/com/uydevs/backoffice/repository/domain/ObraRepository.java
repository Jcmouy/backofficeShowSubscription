package com.uydevs.backoffice.repository.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.repository.AbstractEntidadRepository;

@Repository
public interface ObraRepository extends AbstractEntidadRepository<Obra> {

	@Query(value = "select distinct obra from Obra obra left join fetch obra.etiquetas", countQuery = "select count(distinct obra) from Obra obra")
	Page<Obra> findAllWithEagerRelationships(Pageable pageable);

	@Query("select distinct obra from Obra obra left join fetch obra.etiquetas")
	List<Obra> findAllWithEagerRelationships();

	@Query("select obra from Obra obra left join fetch obra.etiquetas where obra.id =:id")
	Optional<Obra> findOneWithEagerRelationships(@Param("id") Long id);
}
