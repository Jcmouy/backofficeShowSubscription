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
import com.uydevs.backoffice.domain.Moneda;
import com.uydevs.backoffice.dto.domain.MonedaDTO;
import com.uydevs.backoffice.repository.domain.MonedaRepository;
import com.uydevs.backoffice.service.mapper.MonedaMapper;
import com.uydevs.backoffice.web.rest.domain.MonedaResource;

/**
 * Integration tests for the {@link MonedaResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MonedaResourceIT {

	private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
	private static final String UPDATED_CODIGO = "BBBBBBBBBB";

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	@Autowired
	private MonedaRepository monedaRepository;

	@Autowired
	private MonedaMapper monedaMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restMonedaMockMvc;

	private Moneda moneda;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Moneda createEntity(EntityManager em) {
		Moneda moneda = new Moneda().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE);
		return moneda;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Moneda createUpdatedEntity(EntityManager em) {
		Moneda moneda = new Moneda().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
		return moneda;
	}

	@BeforeEach
	public void initTest() {
		moneda = createEntity(em);
	}

	@Test
	@Transactional
	public void createMoneda() throws Exception {
		int databaseSizeBeforeCreate = monedaRepository.findAll().size();
		// Create the Moneda
		MonedaDTO monedaDTO = monedaMapper.toDto(moneda);
		restMonedaMockMvc.perform(post("/api/monedas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(monedaDTO))).andExpect(status().isCreated());

		// Validate the Moneda in the database
		List<Moneda> monedaList = monedaRepository.findAll();
		assertThat(monedaList).hasSize(databaseSizeBeforeCreate + 1);
		Moneda testMoneda = monedaList.get(monedaList.size() - 1);
		assertThat(testMoneda.getCodigo()).isEqualTo(DEFAULT_CODIGO);
		assertThat(testMoneda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
	}

	@Test
	@Transactional
	public void createMonedaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = monedaRepository.findAll().size();

		// Create the Moneda with an existing ID
		moneda.setId(1L);
		MonedaDTO monedaDTO = monedaMapper.toDto(moneda);

		// An entity with an existing ID cannot be created, so this API call must fail
		restMonedaMockMvc.perform(post("/api/monedas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(monedaDTO))).andExpect(status().isBadRequest());

		// Validate the Moneda in the database
		List<Moneda> monedaList = monedaRepository.findAll();
		assertThat(monedaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkCodigoIsRequired() throws Exception {
		int databaseSizeBeforeTest = monedaRepository.findAll().size();
		// set the field null
		moneda.setCodigo(null);

		// Create the Moneda, which fails.
		MonedaDTO monedaDTO = monedaMapper.toDto(moneda);

		restMonedaMockMvc.perform(post("/api/monedas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(monedaDTO))).andExpect(status().isBadRequest());

		List<Moneda> monedaList = monedaRepository.findAll();
		assertThat(monedaList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNombreIsRequired() throws Exception {
		int databaseSizeBeforeTest = monedaRepository.findAll().size();
		// set the field null
		moneda.setNombre(null);

		// Create the Moneda, which fails.
		MonedaDTO monedaDTO = monedaMapper.toDto(moneda);

		restMonedaMockMvc.perform(post("/api/monedas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(monedaDTO))).andExpect(status().isBadRequest());

		List<Moneda> monedaList = monedaRepository.findAll();
		assertThat(monedaList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllMonedas() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList
		restMonedaMockMvc.perform(get("/api/monedas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(moneda.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
	}

	@Test
	@Transactional
	public void getMoneda() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get the moneda
		restMonedaMockMvc.perform(get("/api/monedas/{id}", moneda.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(moneda.getId().intValue()))
				.andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
	}

	@Test
	@Transactional
	public void getMonedasByIdFiltering() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		Long id = moneda.getId();

		defaultMonedaShouldBeFound("id.equals=" + id);
		defaultMonedaShouldNotBeFound("id.notEquals=" + id);

		defaultMonedaShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultMonedaShouldNotBeFound("id.greaterThan=" + id);

		defaultMonedaShouldBeFound("id.lessThanOrEqual=" + id);
		defaultMonedaShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllMonedasByCodigoIsEqualToSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where codigo equals to DEFAULT_CODIGO
		defaultMonedaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

		// Get all the monedaList where codigo equals to UPDATED_CODIGO
		defaultMonedaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllMonedasByCodigoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where codigo not equals to DEFAULT_CODIGO
		defaultMonedaShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

		// Get all the monedaList where codigo not equals to UPDATED_CODIGO
		defaultMonedaShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllMonedasByCodigoIsInShouldWork() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
		defaultMonedaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

		// Get all the monedaList where codigo equals to UPDATED_CODIGO
		defaultMonedaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllMonedasByCodigoIsNullOrNotNull() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where codigo is not null
		defaultMonedaShouldBeFound("codigo.specified=true");

		// Get all the monedaList where codigo is null
		defaultMonedaShouldNotBeFound("codigo.specified=false");
	}

	@Test
	@Transactional
	public void getAllMonedasByCodigoContainsSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where codigo contains DEFAULT_CODIGO
		defaultMonedaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

		// Get all the monedaList where codigo contains UPDATED_CODIGO
		defaultMonedaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllMonedasByCodigoNotContainsSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where codigo does not contain DEFAULT_CODIGO
		defaultMonedaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

		// Get all the monedaList where codigo does not contain UPDATED_CODIGO
		defaultMonedaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllMonedasByNombreIsEqualToSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where nombre equals to DEFAULT_NOMBRE
		defaultMonedaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

		// Get all the monedaList where nombre equals to UPDATED_NOMBRE
		defaultMonedaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllMonedasByNombreIsNotEqualToSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where nombre not equals to DEFAULT_NOMBRE
		defaultMonedaShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

		// Get all the monedaList where nombre not equals to UPDATED_NOMBRE
		defaultMonedaShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllMonedasByNombreIsInShouldWork() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
		defaultMonedaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

		// Get all the monedaList where nombre equals to UPDATED_NOMBRE
		defaultMonedaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllMonedasByNombreIsNullOrNotNull() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where nombre is not null
		defaultMonedaShouldBeFound("nombre.specified=true");

		// Get all the monedaList where nombre is null
		defaultMonedaShouldNotBeFound("nombre.specified=false");
	}

	@Test
	@Transactional
	public void getAllMonedasByNombreContainsSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where nombre contains DEFAULT_NOMBRE
		defaultMonedaShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

		// Get all the monedaList where nombre contains UPDATED_NOMBRE
		defaultMonedaShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllMonedasByNombreNotContainsSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		// Get all the monedaList where nombre does not contain DEFAULT_NOMBRE
		defaultMonedaShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

		// Get all the monedaList where nombre does not contain UPDATED_NOMBRE
		defaultMonedaShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllMonedasByFuncionesIsEqualToSomething() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);
		Funcion funciones = FuncionResourceIT.createEntity(em);
		em.persist(funciones);
		em.flush();
		moneda.addFunciones(funciones);
		monedaRepository.saveAndFlush(moneda);
		Long funcionesId = funciones.getId();

		// Get all the monedaList where funciones equals to funcionesId
		defaultMonedaShouldBeFound("funcionesId.equals=" + funcionesId);

		// Get all the monedaList where funciones equals to funcionesId + 1
		defaultMonedaShouldNotBeFound("funcionesId.equals=" + (funcionesId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultMonedaShouldBeFound(String filter) throws Exception {
		restMonedaMockMvc.perform(get("/api/monedas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(moneda.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

		// Check, that the count call also returns 1
		restMonedaMockMvc.perform(get("/api/monedas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultMonedaShouldNotBeFound(String filter) throws Exception {
		restMonedaMockMvc.perform(get("/api/monedas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restMonedaMockMvc.perform(get("/api/monedas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingMoneda() throws Exception {
		// Get the moneda
		restMonedaMockMvc.perform(get("/api/monedas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateMoneda() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		int databaseSizeBeforeUpdate = monedaRepository.findAll().size();

		// Update the moneda
		Moneda updatedMoneda = monedaRepository.findById(moneda.getId()).get();
		// Disconnect from session so that the updates on updatedMoneda are not directly
		// saved in db
		em.detach(updatedMoneda);
		updatedMoneda.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
		MonedaDTO monedaDTO = monedaMapper.toDto(updatedMoneda);

		restMonedaMockMvc.perform(put("/api/monedas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(monedaDTO))).andExpect(status().isOk());

		// Validate the Moneda in the database
		List<Moneda> monedaList = monedaRepository.findAll();
		assertThat(monedaList).hasSize(databaseSizeBeforeUpdate);
		Moneda testMoneda = monedaList.get(monedaList.size() - 1);
		assertThat(testMoneda.getCodigo()).isEqualTo(UPDATED_CODIGO);
		assertThat(testMoneda.getNombre()).isEqualTo(UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void updateNonExistingMoneda() throws Exception {
		int databaseSizeBeforeUpdate = monedaRepository.findAll().size();

		// Create the Moneda
		MonedaDTO monedaDTO = monedaMapper.toDto(moneda);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restMonedaMockMvc.perform(put("/api/monedas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(monedaDTO))).andExpect(status().isBadRequest());

		// Validate the Moneda in the database
		List<Moneda> monedaList = monedaRepository.findAll();
		assertThat(monedaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteMoneda() throws Exception {
		// Initialize the database
		monedaRepository.saveAndFlush(moneda);

		int databaseSizeBeforeDelete = monedaRepository.findAll().size();

		// Delete the moneda
		restMonedaMockMvc.perform(delete("/api/monedas/{id}", moneda.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Moneda> monedaList = monedaRepository.findAll();
		assertThat(monedaList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
