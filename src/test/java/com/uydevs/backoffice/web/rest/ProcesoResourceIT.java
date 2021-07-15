package com.uydevs.backoffice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.PlataformaBackofficeApp;
import com.uydevs.backoffice.domain.Proceso;
import com.uydevs.backoffice.dto.domain.ProcesoDTO;
import com.uydevs.backoffice.repository.domain.ProcesoRepository;
import com.uydevs.backoffice.service.mapper.ProcesoMapper;
import com.uydevs.backoffice.web.rest.domain.ProcesoResource;

/**
 * Integration tests for the {@link ProcesoResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcesoResourceIT {

	private static final String DEFAULT_TIPO = "AAAAAAAAAA";
	private static final String UPDATED_TIPO = "BBBBBBBBBB";

	private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

	@Autowired
	private ProcesoRepository procesoRepository;

	@Autowired
	private ProcesoMapper procesoMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restProcesoMockMvc;

	private Proceso proceso;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Proceso createEntity(EntityManager em) {
		Proceso proceso = new Proceso().tipo(DEFAULT_TIPO).fecha(DEFAULT_FECHA);
		return proceso;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Proceso createUpdatedEntity(EntityManager em) {
		Proceso proceso = new Proceso().tipo(UPDATED_TIPO).fecha(UPDATED_FECHA);
		return proceso;
	}

	@BeforeEach
	public void initTest() {
		proceso = createEntity(em);
	}

	@Test
	@Transactional
	public void createProceso() throws Exception {
		int databaseSizeBeforeCreate = procesoRepository.findAll().size();
		// Create the Proceso
		ProcesoDTO procesoDTO = procesoMapper.toDto(proceso);
		restProcesoMockMvc.perform(post("/api/procesos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(procesoDTO))).andExpect(status().isCreated());

		// Validate the Proceso in the database
		List<Proceso> procesoList = procesoRepository.findAll();
		assertThat(procesoList).hasSize(databaseSizeBeforeCreate + 1);
		Proceso testProceso = procesoList.get(procesoList.size() - 1);
		assertThat(testProceso.getTipo()).isEqualTo(DEFAULT_TIPO);
		assertThat(testProceso.getFecha()).isEqualTo(DEFAULT_FECHA);
	}

	@Test
	@Transactional
	public void createProcesoWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = procesoRepository.findAll().size();

		// Create the Proceso with an existing ID
		proceso.setId(1L);
		ProcesoDTO procesoDTO = procesoMapper.toDto(proceso);

		// An entity with an existing ID cannot be created, so this API call must fail
		restProcesoMockMvc.perform(post("/api/procesos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(procesoDTO))).andExpect(status().isBadRequest());

		// Validate the Proceso in the database
		List<Proceso> procesoList = procesoRepository.findAll();
		assertThat(procesoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkTipoIsRequired() throws Exception {
		int databaseSizeBeforeTest = procesoRepository.findAll().size();
		// set the field null
		proceso.setTipo(null);

		// Create the Proceso, which fails.
		ProcesoDTO procesoDTO = procesoMapper.toDto(proceso);

		restProcesoMockMvc.perform(post("/api/procesos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(procesoDTO))).andExpect(status().isBadRequest());

		List<Proceso> procesoList = procesoRepository.findAll();
		assertThat(procesoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkFechaIsRequired() throws Exception {
		int databaseSizeBeforeTest = procesoRepository.findAll().size();
		// set the field null
		proceso.setFecha(null);

		// Create the Proceso, which fails.
		ProcesoDTO procesoDTO = procesoMapper.toDto(proceso);

		restProcesoMockMvc.perform(post("/api/procesos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(procesoDTO))).andExpect(status().isBadRequest());

		List<Proceso> procesoList = procesoRepository.findAll();
		assertThat(procesoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllProcesos() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList
		restProcesoMockMvc.perform(get("/api/procesos?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(proceso.getId().intValue())))
				.andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
	}

	@Test
	@Transactional
	public void getProceso() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get the proceso
		restProcesoMockMvc.perform(get("/api/procesos/{id}", proceso.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(proceso.getId().intValue()))
				.andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
				.andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
	}

	@Test
	@Transactional
	public void getProcesosByIdFiltering() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		Long id = proceso.getId();

		defaultProcesoShouldBeFound("id.equals=" + id);
		defaultProcesoShouldNotBeFound("id.notEquals=" + id);

		defaultProcesoShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultProcesoShouldNotBeFound("id.greaterThan=" + id);

		defaultProcesoShouldBeFound("id.lessThanOrEqual=" + id);
		defaultProcesoShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllProcesosByTipoIsEqualToSomething() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where tipo equals to DEFAULT_TIPO
		defaultProcesoShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

		// Get all the procesoList where tipo equals to UPDATED_TIPO
		defaultProcesoShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllProcesosByTipoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where tipo not equals to DEFAULT_TIPO
		defaultProcesoShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

		// Get all the procesoList where tipo not equals to UPDATED_TIPO
		defaultProcesoShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllProcesosByTipoIsInShouldWork() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where tipo in DEFAULT_TIPO or UPDATED_TIPO
		defaultProcesoShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

		// Get all the procesoList where tipo equals to UPDATED_TIPO
		defaultProcesoShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllProcesosByTipoIsNullOrNotNull() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where tipo is not null
		defaultProcesoShouldBeFound("tipo.specified=true");

		// Get all the procesoList where tipo is null
		defaultProcesoShouldNotBeFound("tipo.specified=false");
	}

	@Test
	@Transactional
	public void getAllProcesosByTipoContainsSomething() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where tipo contains DEFAULT_TIPO
		defaultProcesoShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

		// Get all the procesoList where tipo contains UPDATED_TIPO
		defaultProcesoShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllProcesosByTipoNotContainsSomething() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where tipo does not contain DEFAULT_TIPO
		defaultProcesoShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

		// Get all the procesoList where tipo does not contain UPDATED_TIPO
		defaultProcesoShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllProcesosByFechaIsEqualToSomething() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where fecha equals to DEFAULT_FECHA
		defaultProcesoShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

		// Get all the procesoList where fecha equals to UPDATED_FECHA
		defaultProcesoShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllProcesosByFechaIsNotEqualToSomething() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where fecha not equals to DEFAULT_FECHA
		defaultProcesoShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

		// Get all the procesoList where fecha not equals to UPDATED_FECHA
		defaultProcesoShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllProcesosByFechaIsInShouldWork() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where fecha in DEFAULT_FECHA or UPDATED_FECHA
		defaultProcesoShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

		// Get all the procesoList where fecha equals to UPDATED_FECHA
		defaultProcesoShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllProcesosByFechaIsNullOrNotNull() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		// Get all the procesoList where fecha is not null
		defaultProcesoShouldBeFound("fecha.specified=true");

		// Get all the procesoList where fecha is null
		defaultProcesoShouldNotBeFound("fecha.specified=false");
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultProcesoShouldBeFound(String filter) throws Exception {
		restProcesoMockMvc.perform(get("/api/procesos?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(proceso.getId().intValue())))
				.andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

		// Check, that the count call also returns 1
		restProcesoMockMvc.perform(get("/api/procesos/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultProcesoShouldNotBeFound(String filter) throws Exception {
		restProcesoMockMvc.perform(get("/api/procesos?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restProcesoMockMvc.perform(get("/api/procesos/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingProceso() throws Exception {
		// Get the proceso
		restProcesoMockMvc.perform(get("/api/procesos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateProceso() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		int databaseSizeBeforeUpdate = procesoRepository.findAll().size();

		// Update the proceso
		Proceso updatedProceso = procesoRepository.findById(proceso.getId()).get();
		// Disconnect from session so that the updates on updatedProceso are not
		// directly saved in db
		em.detach(updatedProceso);
		updatedProceso.tipo(UPDATED_TIPO).fecha(UPDATED_FECHA);
		ProcesoDTO procesoDTO = procesoMapper.toDto(updatedProceso);

		restProcesoMockMvc.perform(put("/api/procesos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(procesoDTO))).andExpect(status().isOk());

		// Validate the Proceso in the database
		List<Proceso> procesoList = procesoRepository.findAll();
		assertThat(procesoList).hasSize(databaseSizeBeforeUpdate);
		Proceso testProceso = procesoList.get(procesoList.size() - 1);
		assertThat(testProceso.getTipo()).isEqualTo(UPDATED_TIPO);
		assertThat(testProceso.getFecha()).isEqualTo(UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void updateNonExistingProceso() throws Exception {
		int databaseSizeBeforeUpdate = procesoRepository.findAll().size();

		// Create the Proceso
		ProcesoDTO procesoDTO = procesoMapper.toDto(proceso);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restProcesoMockMvc.perform(put("/api/procesos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(procesoDTO))).andExpect(status().isBadRequest());

		// Validate the Proceso in the database
		List<Proceso> procesoList = procesoRepository.findAll();
		assertThat(procesoList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteProceso() throws Exception {
		// Initialize the database
		procesoRepository.saveAndFlush(proceso);

		int databaseSizeBeforeDelete = procesoRepository.findAll().size();

		// Delete the proceso
		restProcesoMockMvc.perform(delete("/api/procesos/{id}", proceso.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Proceso> procesoList = procesoRepository.findAll();
		assertThat(procesoList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
