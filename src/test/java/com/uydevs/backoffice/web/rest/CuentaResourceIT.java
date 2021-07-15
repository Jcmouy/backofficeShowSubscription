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

import java.time.LocalDate;
import java.time.ZoneId;
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
import com.uydevs.backoffice.domain.Cuenta;
import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.domain.Persona;
import com.uydevs.backoffice.dto.domain.CuentaDTO;
import com.uydevs.backoffice.repository.domain.CuentaRepository;
import com.uydevs.backoffice.service.mapper.CuentaMapper;
import com.uydevs.backoffice.web.rest.domain.CuentaResource;

/**
 * Integration tests for the {@link CuentaResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CuentaResourceIT {

	private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
	private static final String UPDATED_CODIGO = "BBBBBBBBBB";

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	private static final LocalDate DEFAULT_FECHA_BAJA = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_FECHA_BAJA = LocalDate.now(ZoneId.systemDefault());
	private static final LocalDate SMALLER_FECHA_BAJA = LocalDate.ofEpochDay(-1L);

	@Autowired
	private CuentaRepository cuentaRepository;

	@Autowired
	private CuentaMapper cuentaMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restCuentaMockMvc;

	private Cuenta cuenta;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Cuenta createEntity(EntityManager em) {
		Cuenta cuenta = new Cuenta().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE).fechaBaja(DEFAULT_FECHA_BAJA);
		return cuenta;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Cuenta createUpdatedEntity(EntityManager em) {
		Cuenta cuenta = new Cuenta().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).fechaBaja(UPDATED_FECHA_BAJA);
		return cuenta;
	}

	@BeforeEach
	public void initTest() {
		cuenta = createEntity(em);
	}

	@Test
	@Transactional
	public void createCuenta() throws Exception {
		int databaseSizeBeforeCreate = cuentaRepository.findAll().size();
		// Create the Cuenta
		CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);
		restCuentaMockMvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cuentaDTO))).andExpect(status().isCreated());

		// Validate the Cuenta in the database
		List<Cuenta> cuentaList = cuentaRepository.findAll();
		assertThat(cuentaList).hasSize(databaseSizeBeforeCreate + 1);
		Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
		assertThat(testCuenta.getCodigo()).isEqualTo(DEFAULT_CODIGO);
		assertThat(testCuenta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
		assertThat(testCuenta.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void createCuentaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = cuentaRepository.findAll().size();

		// Create the Cuenta with an existing ID
		cuenta.setId(1L);
		CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

		// An entity with an existing ID cannot be created, so this API call must fail
		restCuentaMockMvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cuentaDTO))).andExpect(status().isBadRequest());

		// Validate the Cuenta in the database
		List<Cuenta> cuentaList = cuentaRepository.findAll();
		assertThat(cuentaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkCodigoIsRequired() throws Exception {
		int databaseSizeBeforeTest = cuentaRepository.findAll().size();
		// set the field null
		cuenta.setCodigo(null);

		// Create the Cuenta, which fails.
		CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

		restCuentaMockMvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cuentaDTO))).andExpect(status().isBadRequest());

		List<Cuenta> cuentaList = cuentaRepository.findAll();
		assertThat(cuentaList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNombreIsRequired() throws Exception {
		int databaseSizeBeforeTest = cuentaRepository.findAll().size();
		// set the field null
		cuenta.setNombre(null);

		// Create the Cuenta, which fails.
		CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

		restCuentaMockMvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cuentaDTO))).andExpect(status().isBadRequest());

		List<Cuenta> cuentaList = cuentaRepository.findAll();
		assertThat(cuentaList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllCuentas() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList
		restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(cuenta.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
				.andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())));
	}

	@Test
	@Transactional
	public void getCuenta() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get the cuenta
		restCuentaMockMvc.perform(get("/api/cuentas/{id}", cuenta.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(cuenta.getId().intValue()))
				.andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
				.andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()));
	}

	@Test
	@Transactional
	public void getCuentasByIdFiltering() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		Long id = cuenta.getId();

		defaultCuentaShouldBeFound("id.equals=" + id);
		defaultCuentaShouldNotBeFound("id.notEquals=" + id);

		defaultCuentaShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultCuentaShouldNotBeFound("id.greaterThan=" + id);

		defaultCuentaShouldBeFound("id.lessThanOrEqual=" + id);
		defaultCuentaShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllCuentasByCodigoIsEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where codigo equals to DEFAULT_CODIGO
		defaultCuentaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

		// Get all the cuentaList where codigo equals to UPDATED_CODIGO
		defaultCuentaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllCuentasByCodigoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where codigo not equals to DEFAULT_CODIGO
		defaultCuentaShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

		// Get all the cuentaList where codigo not equals to UPDATED_CODIGO
		defaultCuentaShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllCuentasByCodigoIsInShouldWork() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
		defaultCuentaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

		// Get all the cuentaList where codigo equals to UPDATED_CODIGO
		defaultCuentaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllCuentasByCodigoIsNullOrNotNull() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where codigo is not null
		defaultCuentaShouldBeFound("codigo.specified=true");

		// Get all the cuentaList where codigo is null
		defaultCuentaShouldNotBeFound("codigo.specified=false");
	}

	@Test
	@Transactional
	public void getAllCuentasByCodigoContainsSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where codigo contains DEFAULT_CODIGO
		defaultCuentaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

		// Get all the cuentaList where codigo contains UPDATED_CODIGO
		defaultCuentaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllCuentasByCodigoNotContainsSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where codigo does not contain DEFAULT_CODIGO
		defaultCuentaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

		// Get all the cuentaList where codigo does not contain UPDATED_CODIGO
		defaultCuentaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllCuentasByNombreIsEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where nombre equals to DEFAULT_NOMBRE
		defaultCuentaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

		// Get all the cuentaList where nombre equals to UPDATED_NOMBRE
		defaultCuentaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllCuentasByNombreIsNotEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where nombre not equals to DEFAULT_NOMBRE
		defaultCuentaShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

		// Get all the cuentaList where nombre not equals to UPDATED_NOMBRE
		defaultCuentaShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllCuentasByNombreIsInShouldWork() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
		defaultCuentaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

		// Get all the cuentaList where nombre equals to UPDATED_NOMBRE
		defaultCuentaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllCuentasByNombreIsNullOrNotNull() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where nombre is not null
		defaultCuentaShouldBeFound("nombre.specified=true");

		// Get all the cuentaList where nombre is null
		defaultCuentaShouldNotBeFound("nombre.specified=false");
	}

	@Test
	@Transactional
	public void getAllCuentasByNombreContainsSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where nombre contains DEFAULT_NOMBRE
		defaultCuentaShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

		// Get all the cuentaList where nombre contains UPDATED_NOMBRE
		defaultCuentaShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllCuentasByNombreNotContainsSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where nombre does not contain DEFAULT_NOMBRE
		defaultCuentaShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

		// Get all the cuentaList where nombre does not contain UPDATED_NOMBRE
		defaultCuentaShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja equals to DEFAULT_FECHA_BAJA
		defaultCuentaShouldBeFound("fechaBaja.equals=" + DEFAULT_FECHA_BAJA);

		// Get all the cuentaList where fechaBaja equals to UPDATED_FECHA_BAJA
		defaultCuentaShouldNotBeFound("fechaBaja.equals=" + UPDATED_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsNotEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja not equals to DEFAULT_FECHA_BAJA
		defaultCuentaShouldNotBeFound("fechaBaja.notEquals=" + DEFAULT_FECHA_BAJA);

		// Get all the cuentaList where fechaBaja not equals to UPDATED_FECHA_BAJA
		defaultCuentaShouldBeFound("fechaBaja.notEquals=" + UPDATED_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsInShouldWork() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja in DEFAULT_FECHA_BAJA or
		// UPDATED_FECHA_BAJA
		defaultCuentaShouldBeFound("fechaBaja.in=" + DEFAULT_FECHA_BAJA + "," + UPDATED_FECHA_BAJA);

		// Get all the cuentaList where fechaBaja equals to UPDATED_FECHA_BAJA
		defaultCuentaShouldNotBeFound("fechaBaja.in=" + UPDATED_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsNullOrNotNull() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja is not null
		defaultCuentaShouldBeFound("fechaBaja.specified=true");

		// Get all the cuentaList where fechaBaja is null
		defaultCuentaShouldNotBeFound("fechaBaja.specified=false");
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja is greater than or equal to
		// DEFAULT_FECHA_BAJA
		defaultCuentaShouldBeFound("fechaBaja.greaterThanOrEqual=" + DEFAULT_FECHA_BAJA);

		// Get all the cuentaList where fechaBaja is greater than or equal to
		// UPDATED_FECHA_BAJA
		defaultCuentaShouldNotBeFound("fechaBaja.greaterThanOrEqual=" + UPDATED_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsLessThanOrEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja is less than or equal to
		// DEFAULT_FECHA_BAJA
		defaultCuentaShouldBeFound("fechaBaja.lessThanOrEqual=" + DEFAULT_FECHA_BAJA);

		// Get all the cuentaList where fechaBaja is less than or equal to
		// SMALLER_FECHA_BAJA
		defaultCuentaShouldNotBeFound("fechaBaja.lessThanOrEqual=" + SMALLER_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsLessThanSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja is less than DEFAULT_FECHA_BAJA
		defaultCuentaShouldNotBeFound("fechaBaja.lessThan=" + DEFAULT_FECHA_BAJA);

		// Get all the cuentaList where fechaBaja is less than UPDATED_FECHA_BAJA
		defaultCuentaShouldBeFound("fechaBaja.lessThan=" + UPDATED_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void getAllCuentasByFechaBajaIsGreaterThanSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		// Get all the cuentaList where fechaBaja is greater than DEFAULT_FECHA_BAJA
		defaultCuentaShouldNotBeFound("fechaBaja.greaterThan=" + DEFAULT_FECHA_BAJA);

		// Get all the cuentaList where fechaBaja is greater than SMALLER_FECHA_BAJA
		defaultCuentaShouldBeFound("fechaBaja.greaterThan=" + SMALLER_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void getAllCuentasByObrasIsEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);
		Obra obras = ObraResourceIT.createEntity(em);
		em.persist(obras);
		em.flush();
		cuenta.addObras(obras);
		cuentaRepository.saveAndFlush(cuenta);
		Long obrasId = obras.getId();

		// Get all the cuentaList where obras equals to obrasId
		defaultCuentaShouldBeFound("obrasId.equals=" + obrasId);

		// Get all the cuentaList where obras equals to obrasId + 1
		defaultCuentaShouldNotBeFound("obrasId.equals=" + (obrasId + 1));
	}

	@Test
	@Transactional
	public void getAllCuentasByPersonasIsEqualToSomething() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);
		Persona personas = PersonaResourceIT.createEntity(em);
		em.persist(personas);
		em.flush();
		cuenta.addPersonas(personas);
		cuentaRepository.saveAndFlush(cuenta);
		Long personasId = personas.getId();

		// Get all the cuentaList where personas equals to personasId
		defaultCuentaShouldBeFound("personasId.equals=" + personasId);

		// Get all the cuentaList where personas equals to personasId + 1
		defaultCuentaShouldNotBeFound("personasId.equals=" + (personasId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultCuentaShouldBeFound(String filter) throws Exception {
		restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(cuenta.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
				.andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())));

		// Check, that the count call also returns 1
		restCuentaMockMvc.perform(get("/api/cuentas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultCuentaShouldNotBeFound(String filter) throws Exception {
		restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restCuentaMockMvc.perform(get("/api/cuentas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingCuenta() throws Exception {
		// Get the cuenta
		restCuentaMockMvc.perform(get("/api/cuentas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateCuenta() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

		// Update the cuenta
		Cuenta updatedCuenta = cuentaRepository.findById(cuenta.getId()).get();
		// Disconnect from session so that the updates on updatedCuenta are not directly
		// saved in db
		em.detach(updatedCuenta);
		updatedCuenta.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).fechaBaja(UPDATED_FECHA_BAJA);
		CuentaDTO cuentaDTO = cuentaMapper.toDto(updatedCuenta);

		restCuentaMockMvc.perform(put("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cuentaDTO))).andExpect(status().isOk());

		// Validate the Cuenta in the database
		List<Cuenta> cuentaList = cuentaRepository.findAll();
		assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
		Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
		assertThat(testCuenta.getCodigo()).isEqualTo(UPDATED_CODIGO);
		assertThat(testCuenta.getNombre()).isEqualTo(UPDATED_NOMBRE);
		assertThat(testCuenta.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
	}

	@Test
	@Transactional
	public void updateNonExistingCuenta() throws Exception {
		int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

		// Create the Cuenta
		CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restCuentaMockMvc.perform(put("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cuentaDTO))).andExpect(status().isBadRequest());

		// Validate the Cuenta in the database
		List<Cuenta> cuentaList = cuentaRepository.findAll();
		assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteCuenta() throws Exception {
		// Initialize the database
		cuentaRepository.saveAndFlush(cuenta);

		int databaseSizeBeforeDelete = cuentaRepository.findAll().size();

		// Delete the cuenta
		restCuentaMockMvc.perform(delete("/api/cuentas/{id}", cuenta.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Cuenta> cuentaList = cuentaRepository.findAll();
		assertThat(cuentaList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
