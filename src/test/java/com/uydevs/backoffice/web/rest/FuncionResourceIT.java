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

import java.math.BigDecimal;
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
import com.uydevs.backoffice.domain.Funcion;
import com.uydevs.backoffice.domain.Moneda;
import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.domain.Pais;
import com.uydevs.backoffice.domain.Subscripcion;
import com.uydevs.backoffice.dto.domain.FuncionDTO;
import com.uydevs.backoffice.repository.domain.FuncionRepository;
import com.uydevs.backoffice.service.mapper.FuncionMapper;
import com.uydevs.backoffice.web.rest.domain.FuncionResource;

/**
 * Integration tests for the {@link FuncionResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FuncionResourceIT {

	private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
	private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

	private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(1);
	private static final BigDecimal UPDATED_PRECIO = new BigDecimal(2);
	private static final BigDecimal SMALLER_PRECIO = new BigDecimal(1 - 1);

	@Autowired
	private FuncionRepository funcionRepository;

	@Autowired
	private FuncionMapper funcionMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restFuncionMockMvc;

	private Funcion funcion;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Funcion createEntity(EntityManager em) {
		Funcion funcion = new Funcion().fecha(DEFAULT_FECHA).precio(DEFAULT_PRECIO);
		// Add required entity
		Obra obra;
		if (TestUtil.findAll(em, Obra.class).isEmpty()) {
			obra = ObraResourceIT.createEntity(em);
			em.persist(obra);
			em.flush();
		} else {
			obra = TestUtil.findAll(em, Obra.class).get(0);
		}
		funcion.setObra(obra);
		// Add required entity
		Pais pais;
		if (TestUtil.findAll(em, Pais.class).isEmpty()) {
			pais = PaisResourceIT.createEntity(em);
			em.persist(pais);
			em.flush();
		} else {
			pais = TestUtil.findAll(em, Pais.class).get(0);
		}
		funcion.setPais(pais);
		// Add required entity
		Moneda moneda;
		if (TestUtil.findAll(em, Moneda.class).isEmpty()) {
			moneda = MonedaResourceIT.createEntity(em);
			em.persist(moneda);
			em.flush();
		} else {
			moneda = TestUtil.findAll(em, Moneda.class).get(0);
		}
		funcion.setMoneda(moneda);
		return funcion;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Funcion createUpdatedEntity(EntityManager em) {
		Funcion funcion = new Funcion().fecha(UPDATED_FECHA).precio(UPDATED_PRECIO);
		// Add required entity
		Obra obra;
		if (TestUtil.findAll(em, Obra.class).isEmpty()) {
			obra = ObraResourceIT.createUpdatedEntity(em);
			em.persist(obra);
			em.flush();
		} else {
			obra = TestUtil.findAll(em, Obra.class).get(0);
		}
		funcion.setObra(obra);
		// Add required entity
		Pais pais;
		if (TestUtil.findAll(em, Pais.class).isEmpty()) {
			pais = PaisResourceIT.createUpdatedEntity(em);
			em.persist(pais);
			em.flush();
		} else {
			pais = TestUtil.findAll(em, Pais.class).get(0);
		}
		funcion.setPais(pais);
		// Add required entity
		Moneda moneda;
		if (TestUtil.findAll(em, Moneda.class).isEmpty()) {
			moneda = MonedaResourceIT.createUpdatedEntity(em);
			em.persist(moneda);
			em.flush();
		} else {
			moneda = TestUtil.findAll(em, Moneda.class).get(0);
		}
		funcion.setMoneda(moneda);
		return funcion;
	}

	@BeforeEach
	public void initTest() {
		funcion = createEntity(em);
	}

	@Test
	@Transactional
	public void createFuncion() throws Exception {
		int databaseSizeBeforeCreate = funcionRepository.findAll().size();
		// Create the Funcion
		FuncionDTO funcionDTO = funcionMapper.toDto(funcion);
		restFuncionMockMvc.perform(post("/api/funcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(funcionDTO))).andExpect(status().isCreated());

		// Validate the Funcion in the database
		List<Funcion> funcionList = funcionRepository.findAll();
		assertThat(funcionList).hasSize(databaseSizeBeforeCreate + 1);
		Funcion testFuncion = funcionList.get(funcionList.size() - 1);
		assertThat(testFuncion.getFecha()).isEqualTo(DEFAULT_FECHA);
		assertThat(testFuncion.getPrecio()).isEqualTo(DEFAULT_PRECIO);
	}

	@Test
	@Transactional
	public void createFuncionWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = funcionRepository.findAll().size();

		// Create the Funcion with an existing ID
		funcion.setId(1L);
		FuncionDTO funcionDTO = funcionMapper.toDto(funcion);

		// An entity with an existing ID cannot be created, so this API call must fail
		restFuncionMockMvc.perform(post("/api/funcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(funcionDTO))).andExpect(status().isBadRequest());

		// Validate the Funcion in the database
		List<Funcion> funcionList = funcionRepository.findAll();
		assertThat(funcionList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkFechaIsRequired() throws Exception {
		int databaseSizeBeforeTest = funcionRepository.findAll().size();
		// set the field null
		funcion.setFecha(null);

		// Create the Funcion, which fails.
		FuncionDTO funcionDTO = funcionMapper.toDto(funcion);

		restFuncionMockMvc.perform(post("/api/funcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(funcionDTO))).andExpect(status().isBadRequest());

		List<Funcion> funcionList = funcionRepository.findAll();
		assertThat(funcionList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkPrecioIsRequired() throws Exception {
		int databaseSizeBeforeTest = funcionRepository.findAll().size();
		// set the field null
		funcion.setPrecio(null);

		// Create the Funcion, which fails.
		FuncionDTO funcionDTO = funcionMapper.toDto(funcion);

		restFuncionMockMvc.perform(post("/api/funcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(funcionDTO))).andExpect(status().isBadRequest());

		List<Funcion> funcionList = funcionRepository.findAll();
		assertThat(funcionList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllFuncions() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList
		restFuncionMockMvc.perform(get("/api/funcions?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(funcion.getId().intValue())))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
				.andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
	}

	@Test
	@Transactional
	public void getFuncion() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get the funcion
		restFuncionMockMvc.perform(get("/api/funcions/{id}", funcion.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(funcion.getId().intValue()))
				.andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
				.andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
	}

	@Test
	@Transactional
	public void getFuncionsByIdFiltering() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		Long id = funcion.getId();

		defaultFuncionShouldBeFound("id.equals=" + id);
		defaultFuncionShouldNotBeFound("id.notEquals=" + id);

		defaultFuncionShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultFuncionShouldNotBeFound("id.greaterThan=" + id);

		defaultFuncionShouldBeFound("id.lessThanOrEqual=" + id);
		defaultFuncionShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha equals to DEFAULT_FECHA
		defaultFuncionShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

		// Get all the funcionList where fecha equals to UPDATED_FECHA
		defaultFuncionShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsNotEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha not equals to DEFAULT_FECHA
		defaultFuncionShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

		// Get all the funcionList where fecha not equals to UPDATED_FECHA
		defaultFuncionShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsInShouldWork() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha in DEFAULT_FECHA or UPDATED_FECHA
		defaultFuncionShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

		// Get all the funcionList where fecha equals to UPDATED_FECHA
		defaultFuncionShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsNullOrNotNull() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha is not null
		defaultFuncionShouldBeFound("fecha.specified=true");

		// Get all the funcionList where fecha is null
		defaultFuncionShouldNotBeFound("fecha.specified=false");
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha is greater than or equal to DEFAULT_FECHA
		defaultFuncionShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

		// Get all the funcionList where fecha is greater than or equal to UPDATED_FECHA
		defaultFuncionShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsLessThanOrEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha is less than or equal to DEFAULT_FECHA
		defaultFuncionShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

		// Get all the funcionList where fecha is less than or equal to SMALLER_FECHA
		defaultFuncionShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsLessThanSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha is less than DEFAULT_FECHA
		defaultFuncionShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

		// Get all the funcionList where fecha is less than UPDATED_FECHA
		defaultFuncionShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllFuncionsByFechaIsGreaterThanSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where fecha is greater than DEFAULT_FECHA
		defaultFuncionShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

		// Get all the funcionList where fecha is greater than SMALLER_FECHA
		defaultFuncionShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio equals to DEFAULT_PRECIO
		defaultFuncionShouldBeFound("precio.equals=" + DEFAULT_PRECIO);

		// Get all the funcionList where precio equals to UPDATED_PRECIO
		defaultFuncionShouldNotBeFound("precio.equals=" + UPDATED_PRECIO);
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsNotEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio not equals to DEFAULT_PRECIO
		defaultFuncionShouldNotBeFound("precio.notEquals=" + DEFAULT_PRECIO);

		// Get all the funcionList where precio not equals to UPDATED_PRECIO
		defaultFuncionShouldBeFound("precio.notEquals=" + UPDATED_PRECIO);
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsInShouldWork() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio in DEFAULT_PRECIO or UPDATED_PRECIO
		defaultFuncionShouldBeFound("precio.in=" + DEFAULT_PRECIO + "," + UPDATED_PRECIO);

		// Get all the funcionList where precio equals to UPDATED_PRECIO
		defaultFuncionShouldNotBeFound("precio.in=" + UPDATED_PRECIO);
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsNullOrNotNull() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio is not null
		defaultFuncionShouldBeFound("precio.specified=true");

		// Get all the funcionList where precio is null
		defaultFuncionShouldNotBeFound("precio.specified=false");
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio is greater than or equal to
		// DEFAULT_PRECIO
		defaultFuncionShouldBeFound("precio.greaterThanOrEqual=" + DEFAULT_PRECIO);

		// Get all the funcionList where precio is greater than or equal to
		// UPDATED_PRECIO
		defaultFuncionShouldNotBeFound("precio.greaterThanOrEqual=" + UPDATED_PRECIO);
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsLessThanOrEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio is less than or equal to DEFAULT_PRECIO
		defaultFuncionShouldBeFound("precio.lessThanOrEqual=" + DEFAULT_PRECIO);

		// Get all the funcionList where precio is less than or equal to SMALLER_PRECIO
		defaultFuncionShouldNotBeFound("precio.lessThanOrEqual=" + SMALLER_PRECIO);
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsLessThanSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio is less than DEFAULT_PRECIO
		defaultFuncionShouldNotBeFound("precio.lessThan=" + DEFAULT_PRECIO);

		// Get all the funcionList where precio is less than UPDATED_PRECIO
		defaultFuncionShouldBeFound("precio.lessThan=" + UPDATED_PRECIO);
	}

	@Test
	@Transactional
	public void getAllFuncionsByPrecioIsGreaterThanSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		// Get all the funcionList where precio is greater than DEFAULT_PRECIO
		defaultFuncionShouldNotBeFound("precio.greaterThan=" + DEFAULT_PRECIO);

		// Get all the funcionList where precio is greater than SMALLER_PRECIO
		defaultFuncionShouldBeFound("precio.greaterThan=" + SMALLER_PRECIO);
	}

	@Test
	@Transactional
	public void getAllFuncionsBySubscripcionesIsEqualToSomething() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);
		Subscripcion subscripciones = SubscripcionResourceIT.createEntity(em);
		em.persist(subscripciones);
		em.flush();
		funcion.addSubscripciones(subscripciones);
		funcionRepository.saveAndFlush(funcion);
		Long subscripcionesId = subscripciones.getId();

		// Get all the funcionList where subscripciones equals to subscripcionesId
		defaultFuncionShouldBeFound("subscripcionesId.equals=" + subscripcionesId);

		// Get all the funcionList where subscripciones equals to subscripcionesId + 1
		defaultFuncionShouldNotBeFound("subscripcionesId.equals=" + (subscripcionesId + 1));
	}

	@Test
	@Transactional
	public void getAllFuncionsByObraIsEqualToSomething() throws Exception {
		// Get already existing entity
		Obra obra = funcion.getObra();
		funcionRepository.saveAndFlush(funcion);
		Long obraId = obra.getId();

		// Get all the funcionList where obra equals to obraId
		defaultFuncionShouldBeFound("obraId.equals=" + obraId);

		// Get all the funcionList where obra equals to obraId + 1
		defaultFuncionShouldNotBeFound("obraId.equals=" + (obraId + 1));
	}

	@Test
	@Transactional
	public void getAllFuncionsByPaisIsEqualToSomething() throws Exception {
		// Get already existing entity
		Pais pais = funcion.getPais();
		funcionRepository.saveAndFlush(funcion);
		Long paisId = pais.getId();

		// Get all the funcionList where pais equals to paisId
		defaultFuncionShouldBeFound("paisId.equals=" + paisId);

		// Get all the funcionList where pais equals to paisId + 1
		defaultFuncionShouldNotBeFound("paisId.equals=" + (paisId + 1));
	}

	@Test
	@Transactional
	public void getAllFuncionsByMonedaIsEqualToSomething() throws Exception {
		// Get already existing entity
		Moneda moneda = funcion.getMoneda();
		funcionRepository.saveAndFlush(funcion);
		Long monedaId = moneda.getId();

		// Get all the funcionList where moneda equals to monedaId
		defaultFuncionShouldBeFound("monedaId.equals=" + monedaId);

		// Get all the funcionList where moneda equals to monedaId + 1
		defaultFuncionShouldNotBeFound("monedaId.equals=" + (monedaId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultFuncionShouldBeFound(String filter) throws Exception {
		restFuncionMockMvc.perform(get("/api/funcions?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(funcion.getId().intValue())))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
				.andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));

		// Check, that the count call also returns 1
		restFuncionMockMvc.perform(get("/api/funcions/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultFuncionShouldNotBeFound(String filter) throws Exception {
		restFuncionMockMvc.perform(get("/api/funcions?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restFuncionMockMvc.perform(get("/api/funcions/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingFuncion() throws Exception {
		// Get the funcion
		restFuncionMockMvc.perform(get("/api/funcions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateFuncion() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		int databaseSizeBeforeUpdate = funcionRepository.findAll().size();

		// Update the funcion
		Funcion updatedFuncion = funcionRepository.findById(funcion.getId()).get();
		// Disconnect from session so that the updates on updatedFuncion are not
		// directly saved in db
		em.detach(updatedFuncion);
		updatedFuncion.fecha(UPDATED_FECHA).precio(UPDATED_PRECIO);
		FuncionDTO funcionDTO = funcionMapper.toDto(updatedFuncion);

		restFuncionMockMvc.perform(put("/api/funcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(funcionDTO))).andExpect(status().isOk());

		// Validate the Funcion in the database
		List<Funcion> funcionList = funcionRepository.findAll();
		assertThat(funcionList).hasSize(databaseSizeBeforeUpdate);
		Funcion testFuncion = funcionList.get(funcionList.size() - 1);
		assertThat(testFuncion.getFecha()).isEqualTo(UPDATED_FECHA);
		assertThat(testFuncion.getPrecio()).isEqualTo(UPDATED_PRECIO);
	}

	@Test
	@Transactional
	public void updateNonExistingFuncion() throws Exception {
		int databaseSizeBeforeUpdate = funcionRepository.findAll().size();

		// Create the Funcion
		FuncionDTO funcionDTO = funcionMapper.toDto(funcion);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restFuncionMockMvc.perform(put("/api/funcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(funcionDTO))).andExpect(status().isBadRequest());

		// Validate the Funcion in the database
		List<Funcion> funcionList = funcionRepository.findAll();
		assertThat(funcionList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteFuncion() throws Exception {
		// Initialize the database
		funcionRepository.saveAndFlush(funcion);

		int databaseSizeBeforeDelete = funcionRepository.findAll().size();

		// Delete the funcion
		restFuncionMockMvc.perform(delete("/api/funcions/{id}", funcion.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Funcion> funcionList = funcionRepository.findAll();
		assertThat(funcionList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
