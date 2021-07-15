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
import com.uydevs.backoffice.domain.Funcion;
import com.uydevs.backoffice.domain.Pago;
import com.uydevs.backoffice.domain.Persona;
import com.uydevs.backoffice.domain.Subscripcion;
import com.uydevs.backoffice.domain.enumeration.MetodoPago;
import com.uydevs.backoffice.dto.domain.SubscripcionDTO;
import com.uydevs.backoffice.repository.domain.SubscripcionRepository;
import com.uydevs.backoffice.service.mapper.SubscripcionMapper;
import com.uydevs.backoffice.web.rest.domain.SubscripcionResource;

/**
 * Integration tests for the {@link SubscripcionResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubscripcionResourceIT {

	private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

	private static final MetodoPago DEFAULT_METODO_PAGO = MetodoPago.GOOGLEPAY;
	private static final MetodoPago UPDATED_METODO_PAGO = MetodoPago.APPLEPAY;

	@Autowired
	private SubscripcionRepository subscripcionRepository;

	@Autowired
	private SubscripcionMapper subscripcionMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restSubscripcionMockMvc;

	private Subscripcion subscripcion;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Subscripcion createEntity(EntityManager em) {
		Subscripcion subscripcion = new Subscripcion().fecha(DEFAULT_FECHA).metodoPago(DEFAULT_METODO_PAGO);
		// Add required entity
		Funcion funcion;
		if (TestUtil.findAll(em, Funcion.class).isEmpty()) {
			funcion = FuncionResourceIT.createEntity(em);
			em.persist(funcion);
			em.flush();
		} else {
			funcion = TestUtil.findAll(em, Funcion.class).get(0);
		}
		subscripcion.setFuncion(funcion);
		// Add required entity
		Persona persona;
		if (TestUtil.findAll(em, Persona.class).isEmpty()) {
			persona = PersonaResourceIT.createEntity(em);
			em.persist(persona);
			em.flush();
		} else {
			persona = TestUtil.findAll(em, Persona.class).get(0);
		}
		subscripcion.setPersona(persona);
		return subscripcion;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Subscripcion createUpdatedEntity(EntityManager em) {
		Subscripcion subscripcion = new Subscripcion().fecha(UPDATED_FECHA).metodoPago(UPDATED_METODO_PAGO);
		// Add required entity
		Funcion funcion;
		if (TestUtil.findAll(em, Funcion.class).isEmpty()) {
			funcion = FuncionResourceIT.createUpdatedEntity(em);
			em.persist(funcion);
			em.flush();
		} else {
			funcion = TestUtil.findAll(em, Funcion.class).get(0);
		}
		subscripcion.setFuncion(funcion);
		// Add required entity
		Persona persona;
		if (TestUtil.findAll(em, Persona.class).isEmpty()) {
			persona = PersonaResourceIT.createUpdatedEntity(em);
			em.persist(persona);
			em.flush();
		} else {
			persona = TestUtil.findAll(em, Persona.class).get(0);
		}
		subscripcion.setPersona(persona);
		return subscripcion;
	}

	@BeforeEach
	public void initTest() {
		subscripcion = createEntity(em);
	}

	@Test
	@Transactional
	public void createSubscripcion() throws Exception {
		int databaseSizeBeforeCreate = subscripcionRepository.findAll().size();
		// Create the Subscripcion
		SubscripcionDTO subscripcionDTO = subscripcionMapper.toDto(subscripcion);
		restSubscripcionMockMvc.perform(post("/api/subscripcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(subscripcionDTO))).andExpect(status().isCreated());

		// Validate the Subscripcion in the database
		List<Subscripcion> subscripcionList = subscripcionRepository.findAll();
		assertThat(subscripcionList).hasSize(databaseSizeBeforeCreate + 1);
		Subscripcion testSubscripcion = subscripcionList.get(subscripcionList.size() - 1);
		assertThat(testSubscripcion.getFecha()).isEqualTo(DEFAULT_FECHA);
		assertThat(testSubscripcion.getMetodoPago()).isEqualTo(DEFAULT_METODO_PAGO);
	}

	@Test
	@Transactional
	public void createSubscripcionWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = subscripcionRepository.findAll().size();

		// Create the Subscripcion with an existing ID
		subscripcion.setId(1L);
		SubscripcionDTO subscripcionDTO = subscripcionMapper.toDto(subscripcion);

		// An entity with an existing ID cannot be created, so this API call must fail
		restSubscripcionMockMvc
				.perform(post("/api/subscripcions").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subscripcionDTO)))
				.andExpect(status().isBadRequest());

		// Validate the Subscripcion in the database
		List<Subscripcion> subscripcionList = subscripcionRepository.findAll();
		assertThat(subscripcionList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllSubscripcions() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList
		restSubscripcionMockMvc.perform(get("/api/subscripcions?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(subscripcion.getId().intValue())))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
				.andExpect(jsonPath("$.[*].metodoPago").value(hasItem(DEFAULT_METODO_PAGO.toString())));
	}

	@Test
	@Transactional
	public void getSubscripcion() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get the subscripcion
		restSubscripcionMockMvc.perform(get("/api/subscripcions/{id}", subscripcion.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(subscripcion.getId().intValue()))
				.andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
				.andExpect(jsonPath("$.metodoPago").value(DEFAULT_METODO_PAGO.toString()));
	}

	@Test
	@Transactional
	public void getSubscripcionsByIdFiltering() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		Long id = subscripcion.getId();

		defaultSubscripcionShouldBeFound("id.equals=" + id);
		defaultSubscripcionShouldNotBeFound("id.notEquals=" + id);

		defaultSubscripcionShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultSubscripcionShouldNotBeFound("id.greaterThan=" + id);

		defaultSubscripcionShouldBeFound("id.lessThanOrEqual=" + id);
		defaultSubscripcionShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByFechaIsEqualToSomething() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where fecha equals to DEFAULT_FECHA
		defaultSubscripcionShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

		// Get all the subscripcionList where fecha equals to UPDATED_FECHA
		defaultSubscripcionShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByFechaIsNotEqualToSomething() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where fecha not equals to DEFAULT_FECHA
		defaultSubscripcionShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

		// Get all the subscripcionList where fecha not equals to UPDATED_FECHA
		defaultSubscripcionShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByFechaIsInShouldWork() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where fecha in DEFAULT_FECHA or UPDATED_FECHA
		defaultSubscripcionShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

		// Get all the subscripcionList where fecha equals to UPDATED_FECHA
		defaultSubscripcionShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByFechaIsNullOrNotNull() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where fecha is not null
		defaultSubscripcionShouldBeFound("fecha.specified=true");

		// Get all the subscripcionList where fecha is null
		defaultSubscripcionShouldNotBeFound("fecha.specified=false");
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByMetodoPagoIsEqualToSomething() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where metodoPago equals to DEFAULT_METODO_PAGO
		defaultSubscripcionShouldBeFound("metodoPago.equals=" + DEFAULT_METODO_PAGO);

		// Get all the subscripcionList where metodoPago equals to UPDATED_METODO_PAGO
		defaultSubscripcionShouldNotBeFound("metodoPago.equals=" + UPDATED_METODO_PAGO);
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByMetodoPagoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where metodoPago not equals to
		// DEFAULT_METODO_PAGO
		defaultSubscripcionShouldNotBeFound("metodoPago.notEquals=" + DEFAULT_METODO_PAGO);

		// Get all the subscripcionList where metodoPago not equals to
		// UPDATED_METODO_PAGO
		defaultSubscripcionShouldBeFound("metodoPago.notEquals=" + UPDATED_METODO_PAGO);
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByMetodoPagoIsInShouldWork() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where metodoPago in DEFAULT_METODO_PAGO or
		// UPDATED_METODO_PAGO
		defaultSubscripcionShouldBeFound("metodoPago.in=" + DEFAULT_METODO_PAGO + "," + UPDATED_METODO_PAGO);

		// Get all the subscripcionList where metodoPago equals to UPDATED_METODO_PAGO
		defaultSubscripcionShouldNotBeFound("metodoPago.in=" + UPDATED_METODO_PAGO);
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByMetodoPagoIsNullOrNotNull() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		// Get all the subscripcionList where metodoPago is not null
		defaultSubscripcionShouldBeFound("metodoPago.specified=true");

		// Get all the subscripcionList where metodoPago is null
		defaultSubscripcionShouldNotBeFound("metodoPago.specified=false");
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByPagoIsEqualToSomething() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);
		Pago pago = PagoResourceIT.createEntity(em);
		em.persist(pago);
		em.flush();
		subscripcion.setPago(pago);
		subscripcionRepository.saveAndFlush(subscripcion);
		Long pagoId = pago.getId();

		// Get all the subscripcionList where pago equals to pagoId
		defaultSubscripcionShouldBeFound("pagoId.equals=" + pagoId);

		// Get all the subscripcionList where pago equals to pagoId + 1
		defaultSubscripcionShouldNotBeFound("pagoId.equals=" + (pagoId + 1));
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByFuncionIsEqualToSomething() throws Exception {
		// Get already existing entity
		Funcion funcion = subscripcion.getFuncion();
		subscripcionRepository.saveAndFlush(subscripcion);
		Long funcionId = funcion.getId();

		// Get all the subscripcionList where funcion equals to funcionId
		defaultSubscripcionShouldBeFound("funcionId.equals=" + funcionId);

		// Get all the subscripcionList where funcion equals to funcionId + 1
		defaultSubscripcionShouldNotBeFound("funcionId.equals=" + (funcionId + 1));
	}

	@Test
	@Transactional
	public void getAllSubscripcionsByPersonaIsEqualToSomething() throws Exception {
		// Get already existing entity
		Persona persona = subscripcion.getPersona();
		subscripcionRepository.saveAndFlush(subscripcion);
		Long personaId = persona.getId();

		// Get all the subscripcionList where persona equals to personaId
		defaultSubscripcionShouldBeFound("personaId.equals=" + personaId);

		// Get all the subscripcionList where persona equals to personaId + 1
		defaultSubscripcionShouldNotBeFound("personaId.equals=" + (personaId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultSubscripcionShouldBeFound(String filter) throws Exception {
		restSubscripcionMockMvc.perform(get("/api/subscripcions?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(subscripcion.getId().intValue())))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
				.andExpect(jsonPath("$.[*].metodoPago").value(hasItem(DEFAULT_METODO_PAGO.toString())));

		// Check, that the count call also returns 1
		restSubscripcionMockMvc.perform(get("/api/subscripcions/count?sort=id,desc&" + filter))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultSubscripcionShouldNotBeFound(String filter) throws Exception {
		restSubscripcionMockMvc.perform(get("/api/subscripcions?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restSubscripcionMockMvc.perform(get("/api/subscripcions/count?sort=id,desc&" + filter))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingSubscripcion() throws Exception {
		// Get the subscripcion
		restSubscripcionMockMvc.perform(get("/api/subscripcions/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateSubscripcion() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		int databaseSizeBeforeUpdate = subscripcionRepository.findAll().size();

		// Update the subscripcion
		Subscripcion updatedSubscripcion = subscripcionRepository.findById(subscripcion.getId()).get();
		// Disconnect from session so that the updates on updatedSubscripcion are not
		// directly saved in db
		em.detach(updatedSubscripcion);
		updatedSubscripcion.fecha(UPDATED_FECHA).metodoPago(UPDATED_METODO_PAGO);
		SubscripcionDTO subscripcionDTO = subscripcionMapper.toDto(updatedSubscripcion);

		restSubscripcionMockMvc.perform(put("/api/subscripcions").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(subscripcionDTO))).andExpect(status().isOk());

		// Validate the Subscripcion in the database
		List<Subscripcion> subscripcionList = subscripcionRepository.findAll();
		assertThat(subscripcionList).hasSize(databaseSizeBeforeUpdate);
		Subscripcion testSubscripcion = subscripcionList.get(subscripcionList.size() - 1);
		assertThat(testSubscripcion.getFecha()).isEqualTo(UPDATED_FECHA);
		assertThat(testSubscripcion.getMetodoPago()).isEqualTo(UPDATED_METODO_PAGO);
	}

	@Test
	@Transactional
	public void updateNonExistingSubscripcion() throws Exception {
		int databaseSizeBeforeUpdate = subscripcionRepository.findAll().size();

		// Create the Subscripcion
		SubscripcionDTO subscripcionDTO = subscripcionMapper.toDto(subscripcion);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restSubscripcionMockMvc
				.perform(put("/api/subscripcions").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subscripcionDTO)))
				.andExpect(status().isBadRequest());

		// Validate the Subscripcion in the database
		List<Subscripcion> subscripcionList = subscripcionRepository.findAll();
		assertThat(subscripcionList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteSubscripcion() throws Exception {
		// Initialize the database
		subscripcionRepository.saveAndFlush(subscripcion);

		int databaseSizeBeforeDelete = subscripcionRepository.findAll().size();

		// Delete the subscripcion
		restSubscripcionMockMvc
				.perform(delete("/api/subscripcions/{id}", subscripcion.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Subscripcion> subscripcionList = subscripcionRepository.findAll();
		assertThat(subscripcionList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
