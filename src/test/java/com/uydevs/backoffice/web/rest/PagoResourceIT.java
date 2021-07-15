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
import com.uydevs.backoffice.domain.Pago;
import com.uydevs.backoffice.domain.Subscripcion;
import com.uydevs.backoffice.dto.domain.PagoDTO;
import com.uydevs.backoffice.repository.domain.PagoRepository;
import com.uydevs.backoffice.service.mapper.PagoMapper;
import com.uydevs.backoffice.web.rest.domain.PagoResource;

/**
 * Integration tests for the {@link PagoResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PagoResourceIT {

	private static final String DEFAULT_ID_EXTERNO = "AAAAAAAAAA";
	private static final String UPDATED_ID_EXTERNO = "BBBBBBBBBB";

	private static final Instant DEFAULT_FECHA_EXTERNA = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_FECHA_EXTERNA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

	@Autowired
	private PagoRepository pagoRepository;

	@Autowired
	private PagoMapper pagoMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restPagoMockMvc;

	private Pago pago;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Pago createEntity(EntityManager em) {
		Pago pago = new Pago().idExterno(DEFAULT_ID_EXTERNO).fechaExterna(DEFAULT_FECHA_EXTERNA);
		// Add required entity
		Subscripcion subscripcion;
		if (TestUtil.findAll(em, Subscripcion.class).isEmpty()) {
			subscripcion = SubscripcionResourceIT.createEntity(em);
			em.persist(subscripcion);
			em.flush();
		} else {
			subscripcion = TestUtil.findAll(em, Subscripcion.class).get(0);
		}
		pago.setSubscripcion(subscripcion);
		return pago;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Pago createUpdatedEntity(EntityManager em) {
		Pago pago = new Pago().idExterno(UPDATED_ID_EXTERNO).fechaExterna(UPDATED_FECHA_EXTERNA);
		// Add required entity
		Subscripcion subscripcion;
		if (TestUtil.findAll(em, Subscripcion.class).isEmpty()) {
			subscripcion = SubscripcionResourceIT.createUpdatedEntity(em);
			em.persist(subscripcion);
			em.flush();
		} else {
			subscripcion = TestUtil.findAll(em, Subscripcion.class).get(0);
		}
		pago.setSubscripcion(subscripcion);
		return pago;
	}

	@BeforeEach
	public void initTest() {
		pago = createEntity(em);
	}

	@Test
	@Transactional
	public void createPago() throws Exception {
		int databaseSizeBeforeCreate = pagoRepository.findAll().size();
		// Create the Pago
		PagoDTO pagoDTO = pagoMapper.toDto(pago);
		restPagoMockMvc.perform(post("/api/pagos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(pagoDTO))).andExpect(status().isCreated());

		// Validate the Pago in the database
		List<Pago> pagoList = pagoRepository.findAll();
		assertThat(pagoList).hasSize(databaseSizeBeforeCreate + 1);
		Pago testPago = pagoList.get(pagoList.size() - 1);
		assertThat(testPago.getIdExterno()).isEqualTo(DEFAULT_ID_EXTERNO);
		assertThat(testPago.getFechaExterna()).isEqualTo(DEFAULT_FECHA_EXTERNA);
	}

	@Test
	@Transactional
	public void createPagoWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = pagoRepository.findAll().size();

		// Create the Pago with an existing ID
		pago.setId(1L);
		PagoDTO pagoDTO = pagoMapper.toDto(pago);

		// An entity with an existing ID cannot be created, so this API call must fail
		restPagoMockMvc.perform(post("/api/pagos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(pagoDTO))).andExpect(status().isBadRequest());

		// Validate the Pago in the database
		List<Pago> pagoList = pagoRepository.findAll();
		assertThat(pagoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllPagos() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList
		restPagoMockMvc.perform(get("/api/pagos?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(pago.getId().intValue())))
				.andExpect(jsonPath("$.[*].idExterno").value(hasItem(DEFAULT_ID_EXTERNO)))
				.andExpect(jsonPath("$.[*].fechaExterna").value(hasItem(DEFAULT_FECHA_EXTERNA.toString())));
	}

	@Test
	@Transactional
	public void getPago() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get the pago
		restPagoMockMvc.perform(get("/api/pagos/{id}", pago.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(pago.getId().intValue()))
				.andExpect(jsonPath("$.idExterno").value(DEFAULT_ID_EXTERNO))
				.andExpect(jsonPath("$.fechaExterna").value(DEFAULT_FECHA_EXTERNA.toString()));
	}

	@Test
	@Transactional
	public void getPagosByIdFiltering() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		Long id = pago.getId();

		defaultPagoShouldBeFound("id.equals=" + id);
		defaultPagoShouldNotBeFound("id.notEquals=" + id);

		defaultPagoShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultPagoShouldNotBeFound("id.greaterThan=" + id);

		defaultPagoShouldBeFound("id.lessThanOrEqual=" + id);
		defaultPagoShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllPagosByIdExternoIsEqualToSomething() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where idExterno equals to DEFAULT_ID_EXTERNO
		defaultPagoShouldBeFound("idExterno.equals=" + DEFAULT_ID_EXTERNO);

		// Get all the pagoList where idExterno equals to UPDATED_ID_EXTERNO
		defaultPagoShouldNotBeFound("idExterno.equals=" + UPDATED_ID_EXTERNO);
	}

	@Test
	@Transactional
	public void getAllPagosByIdExternoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where idExterno not equals to DEFAULT_ID_EXTERNO
		defaultPagoShouldNotBeFound("idExterno.notEquals=" + DEFAULT_ID_EXTERNO);

		// Get all the pagoList where idExterno not equals to UPDATED_ID_EXTERNO
		defaultPagoShouldBeFound("idExterno.notEquals=" + UPDATED_ID_EXTERNO);
	}

	@Test
	@Transactional
	public void getAllPagosByIdExternoIsInShouldWork() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where idExterno in DEFAULT_ID_EXTERNO or
		// UPDATED_ID_EXTERNO
		defaultPagoShouldBeFound("idExterno.in=" + DEFAULT_ID_EXTERNO + "," + UPDATED_ID_EXTERNO);

		// Get all the pagoList where idExterno equals to UPDATED_ID_EXTERNO
		defaultPagoShouldNotBeFound("idExterno.in=" + UPDATED_ID_EXTERNO);
	}

	@Test
	@Transactional
	public void getAllPagosByIdExternoIsNullOrNotNull() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where idExterno is not null
		defaultPagoShouldBeFound("idExterno.specified=true");

		// Get all the pagoList where idExterno is null
		defaultPagoShouldNotBeFound("idExterno.specified=false");
	}

	@Test
	@Transactional
	public void getAllPagosByIdExternoContainsSomething() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where idExterno contains DEFAULT_ID_EXTERNO
		defaultPagoShouldBeFound("idExterno.contains=" + DEFAULT_ID_EXTERNO);

		// Get all the pagoList where idExterno contains UPDATED_ID_EXTERNO
		defaultPagoShouldNotBeFound("idExterno.contains=" + UPDATED_ID_EXTERNO);
	}

	@Test
	@Transactional
	public void getAllPagosByIdExternoNotContainsSomething() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where idExterno does not contain DEFAULT_ID_EXTERNO
		defaultPagoShouldNotBeFound("idExterno.doesNotContain=" + DEFAULT_ID_EXTERNO);

		// Get all the pagoList where idExterno does not contain UPDATED_ID_EXTERNO
		defaultPagoShouldBeFound("idExterno.doesNotContain=" + UPDATED_ID_EXTERNO);
	}

	@Test
	@Transactional
	public void getAllPagosByFechaExternaIsEqualToSomething() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where fechaExterna equals to DEFAULT_FECHA_EXTERNA
		defaultPagoShouldBeFound("fechaExterna.equals=" + DEFAULT_FECHA_EXTERNA);

		// Get all the pagoList where fechaExterna equals to UPDATED_FECHA_EXTERNA
		defaultPagoShouldNotBeFound("fechaExterna.equals=" + UPDATED_FECHA_EXTERNA);
	}

	@Test
	@Transactional
	public void getAllPagosByFechaExternaIsNotEqualToSomething() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where fechaExterna not equals to DEFAULT_FECHA_EXTERNA
		defaultPagoShouldNotBeFound("fechaExterna.notEquals=" + DEFAULT_FECHA_EXTERNA);

		// Get all the pagoList where fechaExterna not equals to UPDATED_FECHA_EXTERNA
		defaultPagoShouldBeFound("fechaExterna.notEquals=" + UPDATED_FECHA_EXTERNA);
	}

	@Test
	@Transactional
	public void getAllPagosByFechaExternaIsInShouldWork() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where fechaExterna in DEFAULT_FECHA_EXTERNA or
		// UPDATED_FECHA_EXTERNA
		defaultPagoShouldBeFound("fechaExterna.in=" + DEFAULT_FECHA_EXTERNA + "," + UPDATED_FECHA_EXTERNA);

		// Get all the pagoList where fechaExterna equals to UPDATED_FECHA_EXTERNA
		defaultPagoShouldNotBeFound("fechaExterna.in=" + UPDATED_FECHA_EXTERNA);
	}

	@Test
	@Transactional
	public void getAllPagosByFechaExternaIsNullOrNotNull() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		// Get all the pagoList where fechaExterna is not null
		defaultPagoShouldBeFound("fechaExterna.specified=true");

		// Get all the pagoList where fechaExterna is null
		defaultPagoShouldNotBeFound("fechaExterna.specified=false");
	}

	@Test
	@Transactional
	public void getAllPagosBySubscripcionIsEqualToSomething() throws Exception {
		// Get already existing entity
		Subscripcion subscripcion = pago.getSubscripcion();
		pagoRepository.saveAndFlush(pago);
		Long subscripcionId = subscripcion.getId();

		// Get all the pagoList where subscripcion equals to subscripcionId
		defaultPagoShouldBeFound("subscripcionId.equals=" + subscripcionId);

		// Get all the pagoList where subscripcion equals to subscripcionId + 1
		defaultPagoShouldNotBeFound("subscripcionId.equals=" + (subscripcionId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultPagoShouldBeFound(String filter) throws Exception {
		restPagoMockMvc.perform(get("/api/pagos?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
// TODO fix test            
//            .andExpect(jsonPath("$.[*].id").value(hasItem(pago.getId().intValue())))
//            .andExpect(jsonPath("$.[*].idExterno").value(hasItem(DEFAULT_ID_EXTERNO)))
//            .andExpect(jsonPath("$.[*].fechaExterna").value(hasItem(DEFAULT_FECHA_EXTERNA.toString())));

		// Check, that the count call also returns 1
//		restPagoMockMvc.perform(get("/api/pagos/count?sort=id,desc&" + filter)).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultPagoShouldNotBeFound(String filter) throws Exception {
		restPagoMockMvc.perform(get("/api/pagos?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restPagoMockMvc.perform(get("/api/pagos/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingPago() throws Exception {
		// Get the pago
		restPagoMockMvc.perform(get("/api/pagos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updatePago() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

		// Update the pago
		Pago updatedPago = pagoRepository.findById(pago.getId()).get();
		// Disconnect from session so that the updates on updatedPago are not directly
		// saved in db
		em.detach(updatedPago);
		updatedPago.idExterno(UPDATED_ID_EXTERNO).fechaExterna(UPDATED_FECHA_EXTERNA);
		PagoDTO pagoDTO = pagoMapper.toDto(updatedPago);

		restPagoMockMvc.perform(put("/api/pagos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(pagoDTO))).andExpect(status().isOk());

		// Validate the Pago in the database
		List<Pago> pagoList = pagoRepository.findAll();
		assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
		Pago testPago = pagoList.get(pagoList.size() - 1);
		assertThat(testPago.getIdExterno()).isEqualTo(UPDATED_ID_EXTERNO);
		assertThat(testPago.getFechaExterna()).isEqualTo(UPDATED_FECHA_EXTERNA);
	}

	@Test
	@Transactional
	public void updateNonExistingPago() throws Exception {
		int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

		// Create the Pago
		PagoDTO pagoDTO = pagoMapper.toDto(pago);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restPagoMockMvc.perform(put("/api/pagos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(pagoDTO))).andExpect(status().isBadRequest());

		// Validate the Pago in the database
		List<Pago> pagoList = pagoRepository.findAll();
		assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deletePago() throws Exception {
		// Initialize the database
		pagoRepository.saveAndFlush(pago);

		int databaseSizeBeforeDelete = pagoRepository.findAll().size();

		// Delete the pago
		restPagoMockMvc.perform(delete("/api/pagos/{id}", pago.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Pago> pagoList = pagoRepository.findAll();
		assertThat(pagoList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
