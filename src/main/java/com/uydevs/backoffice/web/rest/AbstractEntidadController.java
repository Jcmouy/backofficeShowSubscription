package com.uydevs.backoffice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;
import com.uydevs.backoffice.dto.AbstractEntidadDto;
import com.uydevs.backoffice.service.AbstractQueryService;
import com.uydevs.backoffice.service.EntidadService;
import com.uydevs.backoffice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

public abstract class AbstractEntidadController<D extends AbstractEntidadDto, F extends AbstractEntidadCriteria> {

	public abstract Logger getLogger();

	public abstract String getEntityName();

	public abstract String getApiUrl();

	public abstract EntidadService<D> getEntidadService();

	public abstract AbstractQueryService<?, D, F> getEntidadQueryService();

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	/**
	 * {@code POST  /entidades} : Create a new entidad.
	 *
	 * @param dto the dto of entity to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new dto, or with status {@code 400 (Bad Request)} if the
	 *         entity has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("")
	public ResponseEntity<D> createEntidad(@Valid @RequestBody D dto) throws URISyntaxException {
		getLogger().debug("REST request to save {} : {}", getEntityName(), dto);
		if (dto.getId() != null) {
			throw new BadRequestAlertException("A new " + getEntityName() + " cannot already have an ID",
					getEntityName(), "idexists");
		}
		D result = getEntidadService().save(dto);
		return ResponseEntity
				.created(new URI(getApiUrl() + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, getEntityName(), result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /entidades} : Updates an existing entidad.
	 *
	 * @param dto the dto to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated dto, or with status {@code 400 (Bad Request)} if the dto
	 *         is not valid, or with status {@code 500 (Internal Server Error)} if
	 *         the dto couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("")
	public ResponseEntity<D> updateEntidad(@Valid @RequestBody D dto) throws URISyntaxException {
		getLogger().debug("REST request to update {} : {}", getEntityName(), dto);
		if (dto.getId() == null) {
			throw new BadRequestAlertException("Invalid id", getEntityName(), "idnull");
		}
		D result = getEntidadService().save(dto);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, getEntityName(), dto.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /entidades} : get all the entidades.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of entidades in body.
	 */
	@GetMapping("")
	public ResponseEntity<List<D>> getAllEntidades(F criteria, Pageable pageable) {
		getLogger().debug("REST request to get {} by criteria: {}", getEntityName(), criteria);
		Page<D> page = getEntidadQueryService().findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /entidades/count} : count all the entidades.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/count")
	public ResponseEntity<Long> countEntidades(F criteria) {
		getLogger().debug("REST request to count {} by criteria: {}", getEntityName(), criteria);
		return ResponseEntity.ok().body(getEntidadQueryService().countByCriteria(criteria));
	}

	/**
	 * {@code GET  /entidades/:id} : get the "id" entidad.
	 *
	 * @param id the id of the dto to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the dto, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<D> getEntidad(@PathVariable Long id) {
		getLogger().debug("REST request to get {} : {}", getEntityName(), id);
		Optional<D> dto = getEntidadService().findOne(id);
		return ResponseUtil.wrapOrNotFound(dto);
	}

	/**
	 * {@code DELETE  /entidades/:id} : delete the "id" entidad.
	 *
	 * @param id the id of the dto to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEntidad(@PathVariable Long id) {
		getLogger().debug("REST request to delete {} : {}", getEntityName(), id);
		getEntidadService().delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, getEntityName(), id.toString()))
				.build();
	}
}
