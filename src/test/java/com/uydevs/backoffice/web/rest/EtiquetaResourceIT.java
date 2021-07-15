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
import com.uydevs.backoffice.domain.Etiqueta;
import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.dto.domain.EtiquetaDTO;
import com.uydevs.backoffice.repository.domain.EtiquetaRepository;
import com.uydevs.backoffice.service.mapper.EtiquetaMapper;
import com.uydevs.backoffice.web.rest.domain.EtiquetaResource;

/**
 * Integration tests for the {@link EtiquetaResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EtiquetaResourceIT {

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	@Autowired
	private EtiquetaRepository etiquetaRepository;

	@Autowired
	private EtiquetaMapper etiquetaMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restEtiquetaMockMvc;

	private Etiqueta etiqueta;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Etiqueta createEntity(EntityManager em) {
		Etiqueta etiqueta = new Etiqueta().nombre(DEFAULT_NOMBRE);
		return etiqueta;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Etiqueta createUpdatedEntity(EntityManager em) {
		Etiqueta etiqueta = new Etiqueta().nombre(UPDATED_NOMBRE);
		return etiqueta;
	}

	@BeforeEach
	public void initTest() {
		etiqueta = createEntity(em);
	}

	@Test
	@Transactional
	public void createEtiqueta() throws Exception {
		int databaseSizeBeforeCreate = etiquetaRepository.findAll().size();
		// Create the Etiqueta
		EtiquetaDTO etiquetaDTO = etiquetaMapper.toDto(etiqueta);
		restEtiquetaMockMvc.perform(post("/api/etiquetas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(etiquetaDTO))).andExpect(status().isCreated());

		// Validate the Etiqueta in the database
		List<Etiqueta> etiquetaList = etiquetaRepository.findAll();
		assertThat(etiquetaList).hasSize(databaseSizeBeforeCreate + 1);
		Etiqueta testEtiqueta = etiquetaList.get(etiquetaList.size() - 1);
		assertThat(testEtiqueta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
	}

	@Test
	@Transactional
	public void createEtiquetaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = etiquetaRepository.findAll().size();

		// Create the Etiqueta with an existing ID
		etiqueta.setId(1L);
		EtiquetaDTO etiquetaDTO = etiquetaMapper.toDto(etiqueta);

		// An entity with an existing ID cannot be created, so this API call must fail
		restEtiquetaMockMvc.perform(post("/api/etiquetas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(etiquetaDTO))).andExpect(status().isBadRequest());

		// Validate the Etiqueta in the database
		List<Etiqueta> etiquetaList = etiquetaRepository.findAll();
		assertThat(etiquetaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkNombreIsRequired() throws Exception {
		int databaseSizeBeforeTest = etiquetaRepository.findAll().size();
		// set the field null
		etiqueta.setNombre(null);

		// Create the Etiqueta, which fails.
		EtiquetaDTO etiquetaDTO = etiquetaMapper.toDto(etiqueta);

		restEtiquetaMockMvc.perform(post("/api/etiquetas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(etiquetaDTO))).andExpect(status().isBadRequest());

		List<Etiqueta> etiquetaList = etiquetaRepository.findAll();
		assertThat(etiquetaList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllEtiquetas() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get all the etiquetaList
		restEtiquetaMockMvc.perform(get("/api/etiquetas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(etiqueta.getId().intValue())))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
	}

	@Test
	@Transactional
	public void getEtiqueta() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get the etiqueta
		restEtiquetaMockMvc.perform(get("/api/etiquetas/{id}", etiqueta.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(etiqueta.getId().intValue()))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
	}

	@Test
	@Transactional
	public void getEtiquetasByIdFiltering() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		Long id = etiqueta.getId();

		defaultEtiquetaShouldBeFound("id.equals=" + id);
		defaultEtiquetaShouldNotBeFound("id.notEquals=" + id);

		defaultEtiquetaShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultEtiquetaShouldNotBeFound("id.greaterThan=" + id);

		defaultEtiquetaShouldBeFound("id.lessThanOrEqual=" + id);
		defaultEtiquetaShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllEtiquetasByNombreIsEqualToSomething() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get all the etiquetaList where nombre equals to DEFAULT_NOMBRE
		defaultEtiquetaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

		// Get all the etiquetaList where nombre equals to UPDATED_NOMBRE
		defaultEtiquetaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllEtiquetasByNombreIsNotEqualToSomething() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get all the etiquetaList where nombre not equals to DEFAULT_NOMBRE
		defaultEtiquetaShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

		// Get all the etiquetaList where nombre not equals to UPDATED_NOMBRE
		defaultEtiquetaShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllEtiquetasByNombreIsInShouldWork() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get all the etiquetaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
		defaultEtiquetaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

		// Get all the etiquetaList where nombre equals to UPDATED_NOMBRE
		defaultEtiquetaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllEtiquetasByNombreIsNullOrNotNull() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get all the etiquetaList where nombre is not null
		defaultEtiquetaShouldBeFound("nombre.specified=true");

		// Get all the etiquetaList where nombre is null
		defaultEtiquetaShouldNotBeFound("nombre.specified=false");
	}

	@Test
	@Transactional
	public void getAllEtiquetasByNombreContainsSomething() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get all the etiquetaList where nombre contains DEFAULT_NOMBRE
		defaultEtiquetaShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

		// Get all the etiquetaList where nombre contains UPDATED_NOMBRE
		defaultEtiquetaShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllEtiquetasByNombreNotContainsSomething() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		// Get all the etiquetaList where nombre does not contain DEFAULT_NOMBRE
		defaultEtiquetaShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

		// Get all the etiquetaList where nombre does not contain UPDATED_NOMBRE
		defaultEtiquetaShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllEtiquetasByObrasIsEqualToSomething() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);
		Obra obras = ObraResourceIT.createEntity(em);
		em.persist(obras);
		em.flush();
		etiqueta.addObras(obras);
		etiquetaRepository.saveAndFlush(etiqueta);
		Long obrasId = obras.getId();

		// Get all the etiquetaList where obras equals to obrasId
		defaultEtiquetaShouldBeFound("obrasId.equals=" + obrasId);

		// Get all the etiquetaList where obras equals to obrasId + 1
		defaultEtiquetaShouldNotBeFound("obrasId.equals=" + (obrasId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultEtiquetaShouldBeFound(String filter) throws Exception {
		restEtiquetaMockMvc.perform(get("/api/etiquetas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(etiqueta.getId().intValue())))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

		// Check, that the count call also returns 1
		restEtiquetaMockMvc.perform(get("/api/etiquetas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultEtiquetaShouldNotBeFound(String filter) throws Exception {
		restEtiquetaMockMvc.perform(get("/api/etiquetas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restEtiquetaMockMvc.perform(get("/api/etiquetas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingEtiqueta() throws Exception {
		// Get the etiqueta
		restEtiquetaMockMvc.perform(get("/api/etiquetas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateEtiqueta() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		int databaseSizeBeforeUpdate = etiquetaRepository.findAll().size();

		// Update the etiqueta
		Etiqueta updatedEtiqueta = etiquetaRepository.findById(etiqueta.getId()).get();
		// Disconnect from session so that the updates on updatedEtiqueta are not
		// directly saved in db
		em.detach(updatedEtiqueta);
		updatedEtiqueta.nombre(UPDATED_NOMBRE);
		EtiquetaDTO etiquetaDTO = etiquetaMapper.toDto(updatedEtiqueta);

		restEtiquetaMockMvc.perform(put("/api/etiquetas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(etiquetaDTO))).andExpect(status().isOk());

		// Validate the Etiqueta in the database
		List<Etiqueta> etiquetaList = etiquetaRepository.findAll();
		assertThat(etiquetaList).hasSize(databaseSizeBeforeUpdate);
		Etiqueta testEtiqueta = etiquetaList.get(etiquetaList.size() - 1);
		assertThat(testEtiqueta.getNombre()).isEqualTo(UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void updateNonExistingEtiqueta() throws Exception {
		int databaseSizeBeforeUpdate = etiquetaRepository.findAll().size();

		// Create the Etiqueta
		EtiquetaDTO etiquetaDTO = etiquetaMapper.toDto(etiqueta);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restEtiquetaMockMvc.perform(put("/api/etiquetas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(etiquetaDTO))).andExpect(status().isBadRequest());

		// Validate the Etiqueta in the database
		List<Etiqueta> etiquetaList = etiquetaRepository.findAll();
		assertThat(etiquetaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteEtiqueta() throws Exception {
		// Initialize the database
		etiquetaRepository.saveAndFlush(etiqueta);

		int databaseSizeBeforeDelete = etiquetaRepository.findAll().size();

		// Delete the etiqueta
		restEtiquetaMockMvc.perform(delete("/api/etiquetas/{id}", etiqueta.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Etiqueta> etiquetaList = etiquetaRepository.findAll();
		assertThat(etiquetaList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
