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
import com.uydevs.backoffice.domain.Funcion;
import com.uydevs.backoffice.domain.Pais;
import com.uydevs.backoffice.domain.Persona;
import com.uydevs.backoffice.dto.domain.PaisDTO;
import com.uydevs.backoffice.repository.domain.PaisRepository;
import com.uydevs.backoffice.service.mapper.PaisMapper;
import com.uydevs.backoffice.web.rest.domain.PaisResource;

/**
 * Integration tests for the {@link PaisResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaisResourceIT {

	private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
	private static final String UPDATED_CODIGO = "BBBBBBBBBB";

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private PaisMapper paisMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restPaisMockMvc;

	private Pais pais;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Pais createEntity(EntityManager em) {
		Pais pais = new Pais().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE);
		return pais;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Pais createUpdatedEntity(EntityManager em) {
		Pais pais = new Pais().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
		return pais;
	}

	@BeforeEach
	public void initTest() {
		pais = createEntity(em);
	}

	@Test
	@Transactional
	public void createPais() throws Exception {
		int databaseSizeBeforeCreate = paisRepository.findAll().size();
		// Create the Pais
		PaisDTO paisDTO = paisMapper.toDto(pais);
		restPaisMockMvc.perform(post("/api/pais").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(paisDTO))).andExpect(status().isCreated());

		// Validate the Pais in the database
		List<Pais> paisList = paisRepository.findAll();
		assertThat(paisList).hasSize(databaseSizeBeforeCreate + 1);
		Pais testPais = paisList.get(paisList.size() - 1);
		assertThat(testPais.getCodigo()).isEqualTo(DEFAULT_CODIGO);
		assertThat(testPais.getNombre()).isEqualTo(DEFAULT_NOMBRE);
	}

	@Test
	@Transactional
	public void createPaisWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = paisRepository.findAll().size();

		// Create the Pais with an existing ID
		pais.setId(1L);
		PaisDTO paisDTO = paisMapper.toDto(pais);

		// An entity with an existing ID cannot be created, so this API call must fail
		restPaisMockMvc.perform(post("/api/pais").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(paisDTO))).andExpect(status().isBadRequest());

		// Validate the Pais in the database
		List<Pais> paisList = paisRepository.findAll();
		assertThat(paisList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkCodigoIsRequired() throws Exception {
		int databaseSizeBeforeTest = paisRepository.findAll().size();
		// set the field null
		pais.setCodigo(null);

		// Create the Pais, which fails.
		PaisDTO paisDTO = paisMapper.toDto(pais);

		restPaisMockMvc.perform(post("/api/pais").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(paisDTO))).andExpect(status().isBadRequest());

		List<Pais> paisList = paisRepository.findAll();
		assertThat(paisList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNombreIsRequired() throws Exception {
		int databaseSizeBeforeTest = paisRepository.findAll().size();
		// set the field null
		pais.setNombre(null);

		// Create the Pais, which fails.
		PaisDTO paisDTO = paisMapper.toDto(pais);

		restPaisMockMvc.perform(post("/api/pais").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(paisDTO))).andExpect(status().isBadRequest());

		List<Pais> paisList = paisRepository.findAll();
		assertThat(paisList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllPais() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList
		restPaisMockMvc.perform(get("/api/pais?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
	}

	@Test
	@Transactional
	public void getPais() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get the pais
		restPaisMockMvc.perform(get("/api/pais/{id}", pais.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(pais.getId().intValue()))
				.andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
	}

	@Test
	@Transactional
	public void getPaisByIdFiltering() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		Long id = pais.getId();

		defaultPaisShouldBeFound("id.equals=" + id);
		defaultPaisShouldNotBeFound("id.notEquals=" + id);

		defaultPaisShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultPaisShouldNotBeFound("id.greaterThan=" + id);

		defaultPaisShouldBeFound("id.lessThanOrEqual=" + id);
		defaultPaisShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllPaisByCodigoIsEqualToSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where codigo equals to DEFAULT_CODIGO
		defaultPaisShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

		// Get all the paisList where codigo equals to UPDATED_CODIGO
		defaultPaisShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPaisByCodigoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where codigo not equals to DEFAULT_CODIGO
		defaultPaisShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

		// Get all the paisList where codigo not equals to UPDATED_CODIGO
		defaultPaisShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPaisByCodigoIsInShouldWork() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
		defaultPaisShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

		// Get all the paisList where codigo equals to UPDATED_CODIGO
		defaultPaisShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPaisByCodigoIsNullOrNotNull() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where codigo is not null
		defaultPaisShouldBeFound("codigo.specified=true");

		// Get all the paisList where codigo is null
		defaultPaisShouldNotBeFound("codigo.specified=false");
	}

	@Test
	@Transactional
	public void getAllPaisByCodigoContainsSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where codigo contains DEFAULT_CODIGO
		defaultPaisShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

		// Get all the paisList where codigo contains UPDATED_CODIGO
		defaultPaisShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPaisByCodigoNotContainsSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where codigo does not contain DEFAULT_CODIGO
		defaultPaisShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

		// Get all the paisList where codigo does not contain UPDATED_CODIGO
		defaultPaisShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPaisByNombreIsEqualToSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where nombre equals to DEFAULT_NOMBRE
		defaultPaisShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

		// Get all the paisList where nombre equals to UPDATED_NOMBRE
		defaultPaisShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllPaisByNombreIsNotEqualToSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where nombre not equals to DEFAULT_NOMBRE
		defaultPaisShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

		// Get all the paisList where nombre not equals to UPDATED_NOMBRE
		defaultPaisShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllPaisByNombreIsInShouldWork() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
		defaultPaisShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

		// Get all the paisList where nombre equals to UPDATED_NOMBRE
		defaultPaisShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllPaisByNombreIsNullOrNotNull() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where nombre is not null
		defaultPaisShouldBeFound("nombre.specified=true");

		// Get all the paisList where nombre is null
		defaultPaisShouldNotBeFound("nombre.specified=false");
	}

	@Test
	@Transactional
	public void getAllPaisByNombreContainsSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where nombre contains DEFAULT_NOMBRE
		defaultPaisShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

		// Get all the paisList where nombre contains UPDATED_NOMBRE
		defaultPaisShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllPaisByNombreNotContainsSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		// Get all the paisList where nombre does not contain DEFAULT_NOMBRE
		defaultPaisShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

		// Get all the paisList where nombre does not contain UPDATED_NOMBRE
		defaultPaisShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllPaisByPersonasIsEqualToSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);
		Persona personas = PersonaResourceIT.createEntity(em);
		em.persist(personas);
		em.flush();
		pais.addPersonas(personas);
		paisRepository.saveAndFlush(pais);
		Long personasId = personas.getId();

		// Get all the paisList where personas equals to personasId
		defaultPaisShouldBeFound("personasId.equals=" + personasId);

		// Get all the paisList where personas equals to personasId + 1
		defaultPaisShouldNotBeFound("personasId.equals=" + (personasId + 1));
	}

	@Test
	@Transactional
	public void getAllPaisByFuncionesIsEqualToSomething() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);
		Funcion funciones = FuncionResourceIT.createEntity(em);
		em.persist(funciones);
		em.flush();
		pais.addFunciones(funciones);
		paisRepository.saveAndFlush(pais);
		Long funcionesId = funciones.getId();

		// Get all the paisList where funciones equals to funcionesId
		defaultPaisShouldBeFound("funcionesId.equals=" + funcionesId);

		// Get all the paisList where funciones equals to funcionesId + 1
		defaultPaisShouldNotBeFound("funcionesId.equals=" + (funcionesId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultPaisShouldBeFound(String filter) throws Exception {
		restPaisMockMvc.perform(get("/api/pais?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

		// Check, that the count call also returns 1
		restPaisMockMvc.perform(get("/api/pais/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultPaisShouldNotBeFound(String filter) throws Exception {
		restPaisMockMvc.perform(get("/api/pais?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restPaisMockMvc.perform(get("/api/pais/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingPais() throws Exception {
		// Get the pais
		restPaisMockMvc.perform(get("/api/pais/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updatePais() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		int databaseSizeBeforeUpdate = paisRepository.findAll().size();

		// Update the pais
		Pais updatedPais = paisRepository.findById(pais.getId()).get();
		// Disconnect from session so that the updates on updatedPais are not directly
		// saved in db
		em.detach(updatedPais);
		updatedPais.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
		PaisDTO paisDTO = paisMapper.toDto(updatedPais);

		restPaisMockMvc.perform(put("/api/pais").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(paisDTO))).andExpect(status().isOk());

		// Validate the Pais in the database
		List<Pais> paisList = paisRepository.findAll();
		assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
		Pais testPais = paisList.get(paisList.size() - 1);
		assertThat(testPais.getCodigo()).isEqualTo(UPDATED_CODIGO);
		assertThat(testPais.getNombre()).isEqualTo(UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void updateNonExistingPais() throws Exception {
		int databaseSizeBeforeUpdate = paisRepository.findAll().size();

		// Create the Pais
		PaisDTO paisDTO = paisMapper.toDto(pais);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restPaisMockMvc.perform(put("/api/pais").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(paisDTO))).andExpect(status().isBadRequest());

		// Validate the Pais in the database
		List<Pais> paisList = paisRepository.findAll();
		assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deletePais() throws Exception {
		// Initialize the database
		paisRepository.saveAndFlush(pais);

		int databaseSizeBeforeDelete = paisRepository.findAll().size();

		// Delete the pais
		restPaisMockMvc.perform(delete("/api/pais/{id}", pais.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Pais> paisList = paisRepository.findAll();
		assertThat(paisList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
